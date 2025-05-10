package com.naiyin.healthy.model.dto.sportInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName sport_info
 */
@Data
public class SportInfoDTO implements Serializable {

    /**
     * 运动内容
     */
    private String content;

    /**
     * 发生时间
     */
    private String occurrenceTime;

}