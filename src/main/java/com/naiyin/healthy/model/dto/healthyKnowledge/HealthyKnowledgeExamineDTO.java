package com.naiyin.healthy.model.dto.healthyKnowledge;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @TableName healthy_knowledge
 */
@Data
public class HealthyKnowledgeExamineDTO implements Serializable {

    /**
     * 健康知识id
     */
    private  Long healthyKnowledgeId;

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