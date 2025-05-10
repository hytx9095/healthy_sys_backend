package com.naiyin.healthy.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 
 * @TableName healthy_knowledge
 */
@TableName(value ="healthy_knowledge")
@Data
public class HealthyKnowledge extends Base {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 标签
     */
    private String tags;

    /**
     * 内容
     */
    private String content;

    /**
     * 状态
     */
    private Integer status;

}