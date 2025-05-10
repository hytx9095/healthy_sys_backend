package com.naiyin.healthy.model.dto.testPaper;

import com.naiyin.healthy.common.PageRequest;
import lombok.Data;

@Data
public class TestPaperQueryDTO extends PageRequest {

    /**
     * 试卷id
     */
    private Long teatPaperId;

    /**
     * 分数
     */
    private Double score;
}