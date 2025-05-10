package com.naiyin.healthy.model.dto.healthyNews;

import lombok.Data;

import java.io.Serializable;

@Data
public class HealthyNewsUpdateDTO implements Serializable {

    /**
     * 资讯id
     */
    private Long id;

    /**
     * 是否首页展示
     */
    private Integer isHome;

    /**
     * 资讯内容
     */
    private String content;

    private static final long serialVersionUID = 1L;
}