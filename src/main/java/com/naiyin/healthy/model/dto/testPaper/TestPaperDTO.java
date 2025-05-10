package com.naiyin.healthy.model.dto.testPaper;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class TestPaperDTO implements Serializable {

    /**
     * 试卷id
     */
    private Long teatPaperId;

    /**
     * 用户作答
     */
    private Map<Integer, String> userAnswer;


    private static final long serialVersionUID = 1L;
}