package com.naiyin.healthy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.naiyin.healthy.common.UserContext;
import com.naiyin.healthy.constant.CommonConstant;
import com.naiyin.healthy.enums.SysErrorEnum;
import com.naiyin.healthy.exception.CommonException;
import com.naiyin.healthy.mapper.DietInfoMapper;
import com.naiyin.healthy.mapper.DiseaseInfoMapper;
import com.naiyin.healthy.mapper.SportInfoMapper;
import com.naiyin.healthy.model.dto.suggestions.SuggestionDTO;
import com.naiyin.healthy.model.dto.suggestions.SuggestionQueryDTO;
import com.naiyin.healthy.model.entity.*;
import com.naiyin.healthy.rabbitmq.MQMessageProducer;
import com.naiyin.healthy.service.*;
import com.naiyin.healthy.mapper.SuggestionMapper;
import com.naiyin.healthy.util.HealthyInfoKeyUtils;
import com.naiyin.healthy.util.SqlUtils;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wang'ren
 * @description 针对表【suggestion】的数据库操作Service实现
 * @createDate 2024-12-09 19:21:26
 */
@Service
@RequiredArgsConstructor
public class SuggestionServiceImpl extends ServiceImpl<SuggestionMapper, Suggestion>
        implements SuggestionService {

    private final HealthyKnowledgeService healthyKnowledgeService;
    private final MQMessageProducer mqMessageProducer;
    private final BasicHealthInfoService basicHealthInfoService;
    private final DietInfoMapper dietInfoMapper;
    private final DiseaseInfoMapper diseaseInfoMapper;
    private final SportInfoMapper sportInfoMapper;
    private final HealthyInfoKeyUtils healthyInfoKeyUtils;

    @Override
    public Page<Suggestion> getSuggestions(SuggestionQueryDTO suggestionQueryDTO) {
        Page<Suggestion> page = page(new Page<>(suggestionQueryDTO.getCurrent(), suggestionQueryDTO.getPageSize())
                , getQueryWrapper(suggestionQueryDTO));
        return page;
    }

    @Override
    public void updateSuggestion(SuggestionDTO suggestionDTO) {
        Suggestion suggestion = new Suggestion();
        suggestion.setUserId(UserContext.getUserId());
        BeanUtil.copyProperties(suggestionDTO, suggestion);
        boolean result = updateById(suggestion);
        if (!result) {
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "更新失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void generateSuggestion() {
        // 发送到消息队列
        mqMessageProducer.getSuggestionMessage(JSONUtil.toJsonStr(healthyInfoKeyUtils.getHealthyInfoKeys()));
        // 修改当前建议状态
        List<Suggestion> list = lambdaQuery().eq(Suggestion::getUserId, UserContext.getUserId()).eq(Suggestion::getIsRead, 0).list();

        if (list.size()>0){
            for (Suggestion suggestion : list) {
                suggestion.setIsRead(1);
            }
            boolean b = updateBatchById(list);
            if (!b){
                throw new CommonException(SysErrorEnum.OPERATION_ERROR, "修改失败");
            }
        }
    }

    public QueryWrapper<Suggestion> getQueryWrapper(SuggestionQueryDTO suggestionQueryDTO) {
        if (ObjectUtil.isNull(suggestionQueryDTO)){
            throw new CommonException(SysErrorEnum.PARAM_ERROR, "请求参数为空");
        }
        Integer isRead = suggestionQueryDTO.getIsRead();
        String sortField = suggestionQueryDTO.getSortField();
        String sortOrder = suggestionQueryDTO.getSortOrder();
        QueryWrapper<Suggestion> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotNull(isRead), "is_read", isRead);
        queryWrapper.eq("user_id", UserContext.getUserId());
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        return queryWrapper;
    }
}




