package com.naiyin.healthy.model.dto.doctorInfo;

import lombok.Data;

import java.io.Serializable;

@Data
public class DoctorInfoExamineDTO implements Serializable {

    /**
     * 医生信息id
     */
    private  Long doctorInfoId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 结果
     */
    private String result;

    /**
     * 说明
     */
    private String description;

}