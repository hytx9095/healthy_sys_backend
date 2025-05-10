package com.naiyin.healthy.model.dto.sportInfo;

import com.naiyin.healthy.common.PageRequest;
import lombok.Data;

import java.util.Date;

/**
 * 
 * @TableName sport_info
 */
@Data
public class SportInfoQueryDTO extends PageRequest {

    /**
     * 运动内容
     */
    private String content;

    /**
     * 发生时间
     */
    private Date occurrenceTime;

    private static final long serialVersionUID = 1L;
}