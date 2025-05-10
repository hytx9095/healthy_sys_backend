package com.naiyin.healthy.model.dto.dietInfo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName diet_info
 */
@Data
public class DietInfoUpdateDTO implements Serializable {

    /**
     * 饮食信息id
     */
    private Long dietId;

    /**
     * 饮食类型
     */
    private Integer type;

    /**
     * 具体吃了什么
     */
    private String food;

    /**
     * 发生时间
     */
    private String happenTime;

    private static final long serialVersionUID = 1L;
}