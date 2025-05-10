package com.naiyin.healthy.service;

import com.naiyin.healthy.model.dto.message.MessageDTO;
import com.naiyin.healthy.model.entity.MessageAll;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author wang'ren
* @description 针对表【message_all】的数据库操作Service
* @createDate 2024-12-06 16:57:12
*/
public interface MessageAllService extends IService<MessageAll> {

    void sendMessage(MessageDTO messageDTO);
}
