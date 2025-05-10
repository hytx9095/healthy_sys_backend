package com.naiyin.healthy.model.dto.message;

import com.naiyin.healthy.common.PageRequest;
import lombok.Data;

@Data
public class MessageQueryDTO extends PageRequest {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 状态
     */
    private String searchCondition;

    /**
     * 搜索内容
     */
    private String content;
}