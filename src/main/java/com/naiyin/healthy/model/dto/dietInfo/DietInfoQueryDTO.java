package com.naiyin.healthy.model.dto.dietInfo;

import com.naiyin.healthy.common.PageRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName diet_info
 */
@Data
public class DietInfoQueryDTO extends PageRequest {

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
    private Date happenTime;
}