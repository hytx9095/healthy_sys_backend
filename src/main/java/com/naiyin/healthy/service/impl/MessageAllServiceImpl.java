package com.naiyin.healthy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.naiyin.healthy.enums.SysErrorEnum;
import com.naiyin.healthy.exception.CommonException;
import com.naiyin.healthy.model.dto.message.MessageDTO;
import com.naiyin.healthy.model.entity.MessageAll;
import com.naiyin.healthy.service.MessageAllService;
import com.naiyin.healthy.mapper.MessageAllMapper;
import org.springframework.stereotype.Service;

/**
* @author wang'ren
* @description 针对表【message_all】的数据库操作Service实现
* @createDate 2024-12-06 16:57:12
*/
@Service
public class MessageAllServiceImpl extends ServiceImpl<MessageAllMapper, MessageAll>
    implements MessageAllService{

    @Override
    public void sendMessage(MessageDTO messageDTO) {
        MessageAll messageAll = BeanUtil.copyProperties(messageDTO, MessageAll.class);
        boolean save = save(messageAll);
        if (!save) {
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "发送消息失败");
        }
    }
}




