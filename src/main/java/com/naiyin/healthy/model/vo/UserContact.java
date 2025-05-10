package com.naiyin.healthy.model.vo;

import cn.hutool.core.date.DateTime;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserContact implements Serializable {
    private Long userId;
    private String username;
    private String userAvatar;
    private String lastMessage;
    private Integer unread;
    private String lastTime;
}
