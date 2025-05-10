package com.naiyin.healthy.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName sport_info
 */
@TableName(value ="sport_info")
@Data
public class SportInfo extends Base {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 运动内容
     */
    private String content;

    /**
     * 发生时间
     */
    private Date occurrenceTime;

}