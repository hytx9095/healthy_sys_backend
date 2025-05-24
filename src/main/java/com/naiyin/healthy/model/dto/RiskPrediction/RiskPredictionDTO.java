package com.naiyin.healthy.model.dto.RiskPrediction;

import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName risk_prediction
 */
@Data
public class RiskPredictionDTO implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 建议标题
     */
    private String title;

    /**
     * 问题
     */
    private String question;

    /**
     * 内容
     */
    private String content;

    /**
     * 是否阅读
     */
    private Integer isRead;

    private static final long serialVersionUID = 1L;
}