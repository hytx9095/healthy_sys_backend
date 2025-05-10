package com.naiyin.healthy.model.dto.message;

import lombok.Data;

import java.io.Serializable;

@Data
public class MessageDTO implements Serializable {

    /**
     * 消息id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 消息类型
     */
    private String messageType;

    /**
     * 内容
     */
    private String content;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 发布时间
     */
    private String publishTime;
}
