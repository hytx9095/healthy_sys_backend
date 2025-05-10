package com.naiyin.healthy.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * @TableName risk_prediction
 */
@TableName(value ="risk_prediction")
@Data
public class RiskPrediction extends Base {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 是否已读
     */
    private Integer isRead;
}