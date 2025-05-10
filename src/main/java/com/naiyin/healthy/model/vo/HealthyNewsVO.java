package com.naiyin.healthy.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class HealthyNewsVO implements Serializable {

    /**
     * 资讯id
     */
    private Long id;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户名
     */
    private String username;

    /**
     * 资讯内容
     */
    private String content;

    /**
     * 是否首页展示
     */
    private Integer isHome;
}
