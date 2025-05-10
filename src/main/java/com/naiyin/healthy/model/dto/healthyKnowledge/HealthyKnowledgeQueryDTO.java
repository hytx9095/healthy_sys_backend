package com.naiyin.healthy.model.dto.healthyKnowledge;

import com.naiyin.healthy.common.PageRequest;
import lombok.Data;

@Data
public class HealthyKnowledgeQueryDTO extends PageRequest {

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

    /**
     * 搜索条件
     */
    private String searchCondition;
}