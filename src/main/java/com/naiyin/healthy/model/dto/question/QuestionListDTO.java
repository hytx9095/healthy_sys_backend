package com.naiyin.healthy.model.dto.question;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QuestionListDTO implements Serializable {

    /**
     * 题目列表
     */
    private List<QuestionDTO> questionList;

    private final static long serialVersionUID = 1L;

}