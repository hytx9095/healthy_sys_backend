package com.naiyin.healthy.model.dto.question;

import com.naiyin.healthy.common.PageRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QuestionQueryDTO extends PageRequest {

    /**
     * 题目
     */
    private String title;

    /**
     * 答案
     */
    private String answer;

    /**
     * 题目标签
     */
    private String tags;

    /**
     * 题目类型
     */
    private Integer type;

}