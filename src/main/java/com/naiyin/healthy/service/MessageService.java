package com.naiyin.healthy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.naiyin.healthy.model.dto.message.MessageDTO;
import com.naiyin.healthy.model.dto.message.MessageQueryDTO;
import com.naiyin.healthy.model.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;
import com.naiyin.healthy.model.vo.MessageVO;

import java.text.ParseException;

/**
* @author wang'ren
* @description 针对表【message】的数据库操作Service
* @createDate 2024-12-06 11:19:44
*/
public interface MessageService extends IService<Message> {

    void sendMessageBySystem(MessageDTO messageDTO);

    IPage<MessageVO> getMessageUserPage(MessageQueryDTO messageQueryDTO);

    IPage<MessageVO> getMessagePage(MessageQueryDTO messageQueryDTO);

    IPage<MessageVO> getMessagePageByStatus(MessageQueryDTO messageQueryDTO);

    void updateSystemMessage(MessageDTO messageDTO);

    void addSystemMessage(MessageDTO messageDTO) throws ParseException;
}
