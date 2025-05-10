package com.naiyin.healthy.model.dto.doctorInfo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName diet_info
 */
@Data
public class DoctorInfoUpdateDTO implements Serializable {

    /**
     * 医生信息id
     */
    private Long doctorId;

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