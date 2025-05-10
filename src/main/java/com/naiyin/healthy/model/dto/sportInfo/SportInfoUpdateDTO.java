package com.naiyin.healthy.model.dto.sportInfo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName sport_info
 */
@Data
public class SportInfoUpdateDTO implements Serializable {

    /**
     * 运动信息id
     */
    private Long sportInfoId;

    /**
     * 运动内容
     */
    private String content;

    /**
     * 发生时间
     */
    private String occurrenceTime;

    private static final long serialVersionUID = 1L;
}