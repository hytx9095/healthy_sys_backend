package com.naiyin.healthy.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * @TableName doctor_dialogue
 */
@TableName(value ="doctor_dialogue")
@Data
public class DoctorDialogue extends Base {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 医生id
     */
    private Long doctorId;

    /**
     * 发言人，用户-user，医生-doctor
     */
    private String spokesman;

    /**
     * 内容
     */
    private String content;

    /**
     * 状态，0：正常，1：加载
     */
    private Integer status;

}