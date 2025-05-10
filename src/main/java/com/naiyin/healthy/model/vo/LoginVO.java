package com.naiyin.healthy.model.vo;

import com.naiyin.healthy.model.entity.Base;
import lombok.*;

@Data
public class LoginVO extends Base {

    /**
     * 用户账号
     */
    private String userAccount;

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
     * 用户角色
     */
    private String role;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * token
     */
    private String token;
}
