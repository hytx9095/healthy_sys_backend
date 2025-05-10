package com.naiyin.healthy.model.dto.healthyNews;

import com.naiyin.healthy.common.PageRequest;
import lombok.Data;

import java.util.Date;

/**
 * 
 * @TableName diet_info
 */
@Data
public class HealthyNewsQueryDTO extends PageRequest {

    /**
     * 是否首页展示
     */
    private Integer isHome;

    /**
     * 内容
     */
    private String content;
}