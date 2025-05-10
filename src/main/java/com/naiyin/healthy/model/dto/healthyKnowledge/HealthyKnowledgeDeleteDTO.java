package com.naiyin.healthy.model.dto.healthyKnowledge;

import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName healthy_knowledge
 */
@Data
public class HealthyKnowledgeDeleteDTO implements Serializable {

    /**
     * 健康知识id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}