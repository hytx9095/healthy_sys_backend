package com.naiyin.healthy.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DietInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 饮食类型
     */
    private String type;

    /**
     * 具体吃了什么
     */
    private String food;

    /**
     * 饮食时间
     */
    private Date happenTime;

}