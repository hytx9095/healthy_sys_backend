package com.naiyin.healthy.model.dto.suggestions;

import com.naiyin.healthy.common.PageRequest;
import lombok.Data;

import java.util.Date;

@Data
public class SuggestionQueryDTO extends PageRequest {

    /**
     * 是否阅读
     */
    private Integer isRead;
}