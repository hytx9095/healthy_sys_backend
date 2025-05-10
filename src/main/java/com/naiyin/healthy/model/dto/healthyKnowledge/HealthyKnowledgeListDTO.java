package com.naiyin.healthy.model.dto.healthyKnowledge;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @TableName healthy_knowledge
 */
@Data
public class HealthyKnowledgeListDTO implements Serializable {

    /**
     * 健康知识集合
     */
    private List<HealthyKnowledgeDTO> healthyKnowledgeDTOS;

}