package com.naiyin.healthy.model.dto.user;

import lombok.*;

import java.io.Serializable;

@Data
public class UserUpdateDTO implements Serializable {

    /**
     * 用户账号
     */
//    private String userAccount;

    /**
     * 电话号码
     */
    private String telephone;

    /**
     * 用户名
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户头像
     */
    private String userAvatar;

    private static final long serialVersionUID = 1L;
}
