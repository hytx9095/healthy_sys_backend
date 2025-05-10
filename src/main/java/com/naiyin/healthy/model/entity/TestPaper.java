package com.naiyin.healthy.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * @TableName test_paper
 */
@TableName(value ="test_paper")
@Data
public class TestPaper extends Base {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 题目信息
     */
    private String questions;

    /**
     * 用户作答
     */
    private String userAnswer;

    /**
     * 答案信息
     */
    private String answer;

    /**
     * 得分
     */
    private Double score;
}