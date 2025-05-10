package com.naiyin.healthy.model.vo;

import lombok.Data;

import java.util.Date;

@Data
public class HealthyKnowledgeVO extends BaseVO {

    /**
     * 健康知识id
     */
    private Long id;
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
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否收藏
     */
    private Integer isStarred;

}