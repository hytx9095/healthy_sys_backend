package com.naiyin.healthy.model.dto.question;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QuestionDTO implements Serializable {

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
    private List<String> wrongAnswers;

    /**
     * 题目标签
     */
    private String tags;

    /**
     * 题目类型
     */
    private Integer type;

    private final static long serialVersionUID = 1L;

}