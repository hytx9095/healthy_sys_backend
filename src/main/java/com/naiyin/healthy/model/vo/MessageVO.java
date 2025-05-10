package com.naiyin.healthy.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MessageVO implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * id
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

    /**
     * 创建时间
     */
    private Date createTime;
}
