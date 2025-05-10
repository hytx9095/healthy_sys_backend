package com.naiyin.healthy.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * @TableName question
 */
@TableName(value ="question")
@Data
public class Question extends Base {

    /**
     * 题目
     */
    private String title;

    /**
     * 答案
     */
    private String answer;

    /**
     * 错误答案
     */
    private String wrongAnswers;

    /**
     * 题目标签
     */
    private String tags;

    /**
     * 题目类型
     */
    private Integer type;

    /**
     * 上传用户id
     */
    private Long userId;

}