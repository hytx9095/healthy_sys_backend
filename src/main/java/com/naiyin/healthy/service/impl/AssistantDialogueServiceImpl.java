package com.naiyin.healthy.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.naiyin.healthy.common.UserContext;
import com.naiyin.healthy.constant.CommonConstant;
import com.naiyin.healthy.enums.AssistantDialogueEnum;
import com.naiyin.healthy.enums.AssistantDialogueStatusEnum;
import com.naiyin.healthy.enums.SysErrorEnum;
import com.naiyin.healthy.exception.CommonException;
import com.naiyin.healthy.model.dto.assistantDialogue.AssistantDialogueDTO;
import com.naiyin.healthy.model.dto.assistantDialogue.AssistantDialogueQueryDTO;
import com.naiyin.healthy.model.dto.assistantDialogue.AssistantDialogueRegenerateDTO;
import com.naiyin.healthy.model.entity.AssistantDialogue;
import com.naiyin.healthy.rabbitmq.MQMessageProducer;
import com.naiyin.healthy.service.AssistantDialogueService;
import com.naiyin.healthy.mapper.AssistantDialogueMapper;
import com.naiyin.healthy.util.SqlUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author wang'ren
* @description 针对表【assistant_dialogue】的数据库操作Service实现
* @createDate 2024-12-09 19:21:14
*/
@Service
public class AssistantDialogueServiceImpl extends ServiceImpl<AssistantDialogueMapper, AssistantDialogue>
    implements AssistantDialogueService{

    @Resource
    private MQMessageProducer mqMessageProducer;

    @Override
    public Page<AssistantDialogue> getAssistantDialoguePage(AssistantDialogueQueryDTO assistantDialogueQueryDTO) {
        assistantDialogueQueryDTO.setUserId(UserContext.getUserId());
        Page<AssistantDialogue> page = page(
                new Page<>(assistantDialogueQueryDTO.getCurrent(), assistantDialogueQueryDTO.getPageSize()),
                getQueryWrapper(assistantDialogueQueryDTO));
        return page;
    }

    @Override
    public void regenerateDialogue(AssistantDialogueRegenerateDTO regenerateDTO) {
        boolean delete = removeById(regenerateDTO.getAnswerId());
        if (!delete) {
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "删除失败");
        }
        // 发送到消息队列
        mqMessageProducer.sendMessageToAssistant(String.valueOf(regenerateDTO.getQuestionId()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void chat(AssistantDialogueDTO assistantDialogueDTO) {
        // 用户对话添加
        AssistantDialogue userDialogue = new AssistantDialogue();
        userDialogue.setUserId(UserContext.getUserId());
        userDialogue.setSpokesman(AssistantDialogueEnum.USER.getValue());
        userDialogue.setContent(assistantDialogueDTO.getContent());
        userDialogue.setStatus(AssistantDialogueStatusEnum.NORMAL.getValue());
        boolean userSave = save(userDialogue);
        if (!userSave){
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "对话添加失败");
        }
        // 小助手对话添加
        AssistantDialogue assistantDialogue = new AssistantDialogue();
        assistantDialogue.setUserId(UserContext.getUserId());
        assistantDialogue.setSpokesman(AssistantDialogueEnum.ASSISTANT.getValue());
        assistantDialogue.setStatus(AssistantDialogueStatusEnum.LOADING.getValue());
        boolean assistantSave = save(assistantDialogue);
        if (!assistantSave){
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "对话添加失败");
        }
        String message = userDialogue.getId() + ":" + assistantDialogue.getId();
        // 发送到消息队列
        mqMessageProducer.sendMessageToAssistant(message);
    }

    @Override
    public QueryWrapper<AssistantDialogue> getQueryWrapper(AssistantDialogueQueryDTO assistantDialogueQueryDTO) {
        if(assistantDialogueQueryDTO == null){
            throw new CommonException(SysErrorEnum.PARAM_ERROR, "请求参数为空");
        }
        String sortField = assistantDialogueQueryDTO.getSortField();
        String sortOrder = assistantDialogueQueryDTO.getSortOrder();
        QueryWrapper<AssistantDialogue> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(assistantDialogueQueryDTO.getUserId() != null, "user_id", assistantDialogueQueryDTO.getUserId());
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        return queryWrapper;
    }
}




