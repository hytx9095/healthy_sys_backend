package com.naiyin.healthy.model.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserUpdateRoleDTO implements Serializable {


    /**
     * 用户id
     */
    private  Long userId;

    /**
     * 角色
     */
    private String role;

    private static final long serialVersionUID = 1L;
}
