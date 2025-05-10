package com.naiyin.healthy.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DoctorInfoVO implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * id
     */
    private Long userId;

    /**
     * 用户昵称
     */
    private String username;

    /**
     * 头像
     */
    private String userAvatar;

    /**
     * 医生类型
     */
    private String type;

    /**
     * 描述
     */
    private String description;

    /**
     * 证明材料
     */
    private String evidence;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

}