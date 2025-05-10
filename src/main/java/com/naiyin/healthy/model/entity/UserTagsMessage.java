package com.naiyin.healthy.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserTagsMessage implements Serializable {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 标签
     */
    private List<String> tags;
}