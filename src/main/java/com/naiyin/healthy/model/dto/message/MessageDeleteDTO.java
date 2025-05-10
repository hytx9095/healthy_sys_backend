package com.naiyin.healthy.model.dto.message;

import lombok.Data;

import java.io.Serializable;

@Data
public class MessageDeleteDTO implements Serializable {

    /**
     * 消息id
     */
    private Long id;

    /**
     * 消息类型
     */
    private String messageType;
}
