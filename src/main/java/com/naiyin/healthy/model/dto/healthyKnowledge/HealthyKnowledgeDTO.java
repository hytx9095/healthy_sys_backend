package com.naiyin.healthy.model.dto.healthyKnowledge;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @TableName healthy_knowledge
 */
@Data
public class HealthyKnowledgeDTO implements Serializable {

    /**
     * 健康知识id
     */
    private Long healthyKnowledgeId;
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

}