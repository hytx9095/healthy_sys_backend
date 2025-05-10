package com.naiyin.healthy.model.dto.suggestions;

import lombok.Data;

import java.io.Serializable;

@Data
public class SuggestionDTO implements Serializable {


    /**
     * id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 建议标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 是否阅读
     */
    private Integer isRead;

    private static final long serialVersionUID = 1L;
}