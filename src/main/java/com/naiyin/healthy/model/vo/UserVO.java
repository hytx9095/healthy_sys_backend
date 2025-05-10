package com.naiyin.healthy.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserVO implements Serializable {

    /**
     * id
     */
    private Long id;
    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 用户密码
     */
//    private String userPassword;

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
     * 创建时间
     */
    private Date createTime;


}
