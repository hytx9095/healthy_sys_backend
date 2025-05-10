package com.naiyin.healthy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.naiyin.healthy.enums.MessageStatusEnum;
import com.naiyin.healthy.enums.MessageTypeEnum;
import com.naiyin.healthy.enums.SysErrorEnum;
import com.naiyin.healthy.exception.CommonException;
import com.naiyin.healthy.model.dto.message.MessageDTO;
import com.naiyin.healthy.model.dto.message.MessageQueryDTO;
import com.naiyin.healthy.model.entity.Message;
import com.naiyin.healthy.model.entity.MessageAll;
import com.naiyin.healthy.model.vo.MessageVO;
import com.naiyin.healthy.service.MessageAllService;
import com.naiyin.healthy.service.MessageService;
import com.naiyin.healthy.mapper.MessageMapper;
import com.naiyin.healthy.service.comon.TaskService;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

/**
* @author wang'ren
* @description 针对表【message】的数据库操作Service实现
* @createDate 2024-12-06 11:19:44
*/
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message>
    implements MessageService{

    @Resource
    private MessageMapper messageMapper;

    @Resource
    private MessageAllService messageAllService;

    @Resource
    private TaskService taskService;

    @Override
    public void sendMessageBySystem(MessageDTO messageDTO) {
        Message message = BeanUtil.copyProperties(messageDTO, Message.class);
    }

    @Override
    public IPage<MessageVO> getMessageUserPage(MessageQueryDTO messageQueryDTO) {
        long current = messageQueryDTO.getCurrent();
        long pageSize = messageQueryDTO.getPageSize();
        Page<MessageVO> page = new Page<>(current, pageSize);
        IPage<MessageVO> userMessages = messageMapper.getUserMessages(page, messageQueryDTO.getUserId());
        return userMessages;
    }

    @Override
    public IPage<MessageVO> getMessagePageByStatus(MessageQueryDTO messageQueryDTO) {
        long current = messageQueryDTO.getCurrent();
        long pageSize = messageQueryDTO.getPageSize();
        String searchCondition = messageQueryDTO.getSearchCondition();
        int status = 0;
        if (searchCondition.equals(MessageStatusEnum.DRAFT.getText())){
            status = MessageStatusEnum.DRAFT.getValue();
        } else if (searchCondition.equals(MessageStatusEnum.PUBLISHED.getText())){
            status = MessageStatusEnum.PUBLISHED.getValue();
        } else {
            status = MessageStatusEnum.SCHEDULED.getValue();
        }
        Page<MessageVO> page = new Page<>(current, pageSize);
        IPage<MessageVO> userMessages = messageMapper.getMessagePageByStatus(page, status, messageQueryDTO.getContent());
        return userMessages;
    }

    @Override
    public IPage<MessageVO> getMessagePage(MessageQueryDTO messageQueryDTO) {
        long current = messageQueryDTO.getCurrent();
        long pageSize = messageQueryDTO.getPageSize();
        Page<MessageVO> page = new Page<>(current, pageSize);
        IPage<MessageVO> userMessages = messageMapper.getMessagePage(page, messageQueryDTO.getContent());
        return userMessages;
    }

    @Override
    public void updateSystemMessage(MessageDTO messageDTO) {
        String messageType = messageDTO.getMessageType();
        Integer status = messageDTO.getStatus();
        if (messageType.equals(MessageTypeEnum.ALL.getValue())){
            MessageAll messageAll = BeanUtil.copyProperties(messageDTO, MessageAll.class);
            if (ObjectUtil.isNotNull(status) && status != MessageStatusEnum.DRAFT.getValue()){
                messageAll.setPublishTime(new Date());
            }
            boolean b = messageAllService.updateById(messageAll);
            if (!b){
                throw new CommonException(SysErrorEnum.OPERATION_ERROR, "更新失败");
            }
        }else {
            Message message = BeanUtil.copyProperties(messageDTO, Message.class);
            if (ObjectUtil.isNotNull(status) && status != MessageStatusEnum.DRAFT.getValue()){
                message.setPublishTime(new Date());
            }
            boolean b = updateById(message);
            if (!b){
                throw new CommonException(SysErrorEnum.OPERATION_ERROR, "更新失败");
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addSystemMessage(MessageDTO messageDTO) throws ParseException {
        String messageType = messageDTO.getMessageType();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (messageType.equals(MessageTypeEnum.ALL.getValue())){
            MessageAll messageAll = BeanUtil.copyProperties(messageDTO, MessageAll.class);
            if (ObjectUtil.isNotNull(messageDTO.getPublishTime())){
                Date date = format.parse(messageDTO.getPublishTime());
                messageAll.setPublishTime(date);
                messageAll.setStatus(MessageStatusEnum.SCHEDULED.getValue());
            }
            boolean b = messageAllService.save(messageAll);
            if (!b){
                throw new CommonException(SysErrorEnum.OPERATION_ERROR, "添加失败");
            }
            if (ObjectUtil.isNotNull(messageDTO.getPublishTime())){
               taskService.addTask(messageAll.getId(),
                       LocalDateTime.parse(messageDTO.getPublishTime().replace(" ", "T")), () -> {
                   messageAll.setStatus(MessageStatusEnum.PUBLISHED.getValue());
                   boolean update = messageAllService.updateById(messageAll);
                   if (!update){
                       throw new CommonException(SysErrorEnum.OPERATION_ERROR, "更新失败");
                   }
               });
            }
        }else {
            Message message = BeanUtil.copyProperties(messageDTO, Message.class);
            if (ObjectUtil.isNotNull(messageDTO.getPublishTime())){
                Date date = format.parse(messageDTO.getPublishTime());
                message.setStatus(MessageStatusEnum.PUBLISHED.getValue());
                message.setPublishTime(date);
            }
            boolean b = save(message);
            if (!b){
                throw new CommonException(SysErrorEnum.OPERATION_ERROR, "添加失败");
            }
            if (ObjectUtil.isNotNull(messageDTO.getPublishTime())){
                taskService.addTask(message.getId(), LocalDateTime.parse(messageDTO.getPublishTime()), () -> {
                    message.setStatus(MessageStatusEnum.PUBLISHED.getValue());
                    boolean update = updateById(message);
                    if (!update){
                        throw new CommonException(SysErrorEnum.OPERATION_ERROR, "更新失败");
                    }
                });
            }
        }
    }
}




