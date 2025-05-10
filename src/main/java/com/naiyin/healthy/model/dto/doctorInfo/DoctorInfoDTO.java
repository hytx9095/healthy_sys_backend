package com.naiyin.healthy.model.dto.doctorInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName diet_info
 */
@Data
public class DoctorInfoDTO implements Serializable {


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



    private static final long serialVersionUID = 1L;
}