package com.naiyin.healthy.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * @TableName healthy_knowledge_star
 */
@TableName(value ="healthy_knowledge_star")
@Data
public class HealthyKnowledgeStar extends Base {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 健康知识id
     */
    private Long healthyKnowledgeId;

}