package com.naiyin.healthy.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * @TableName healthy_knowledge_examine
 */
@TableName(value ="healthy_knowledge_examine")
@Data
public class HealthyKnowledgeExamine extends Base {

    /**
     * 健康知识id
     */
    private Long healthyKnowledgeId;

    /**
     * 审核说明
     */
    private String description;

    /**
     * 是否通过，0：没通过、1：通过
     */
    private Integer result;

}