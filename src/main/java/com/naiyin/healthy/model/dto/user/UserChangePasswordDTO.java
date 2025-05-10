package com.naiyin.healthy.model.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserChangePasswordDTO implements Serializable {

    /**
     * 旧用户密码
     */
    private String oldPassword;

    /**
     * 新用户密码
     */
    private String newPassword;

    /**
     * 新用户密码
     */
    private String confirmPassword;

    private static final long serialVersionUID = 1L;
}
