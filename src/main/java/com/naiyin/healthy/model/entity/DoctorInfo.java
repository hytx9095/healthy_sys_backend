package com.naiyin.healthy.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * @TableName doctor_info
 */
@TableName(value ="doctor_info")
@Data
public class DoctorInfo extends Base {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 医生类型
     */
    private String type;

    /**
     * 医生类型
     */
    private Integer status;

    /**
     * 描述
     */
    private String description;

    /**
     * 证明材料
     */
    private String evidence;

}