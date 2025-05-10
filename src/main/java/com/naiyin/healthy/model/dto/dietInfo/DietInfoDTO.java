package com.naiyin.healthy.model.dto.dietInfo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.naiyin.healthy.model.entity.Base;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName diet_info
 */
@Data
public class DietInfoDTO implements Serializable {

    /**
     * 饮食类型
     */
    private Integer type;

    /**
     * 具体吃了什么
     */
    private String food;

    /**
     * 发生时间（时间戳）
     */
    private String happenTime;

    private static final long serialVersionUID = 1L;
}