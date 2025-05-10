package com.naiyin.healthy.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

import java.util.Date;

/**
 * 
 * @TableName diet_info
 */
@TableName(value ="diet_info")
@Data
public class DietInfo extends Base{

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 饮食类型
     */
    private Integer type;

    /**
     * 具体吃了什么
     */
    private String food;

    /**
     * 饮食时间
     */
    private Date happenTime;

}