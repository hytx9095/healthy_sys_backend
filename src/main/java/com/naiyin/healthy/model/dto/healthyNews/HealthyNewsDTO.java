package com.naiyin.healthy.model.dto.healthyNews;

import lombok.Data;

import java.io.Serializable;

@Data
public class HealthyNewsDTO implements Serializable {

    /**
     * 资讯内容
     */
    private String content;

    /**
     * 是否首页展示
     */
    private Integer isHome;

    private static final long serialVersionUID = 1L;
}