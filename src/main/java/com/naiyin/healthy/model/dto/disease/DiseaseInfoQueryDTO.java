package com.naiyin.healthy.model.dto.disease;

import com.naiyin.healthy.common.PageRequest;
import lombok.Data;

import java.util.Date;

/**
 * 
 * @TableName diet_info
 */
@Data
public class DiseaseInfoQueryDTO extends PageRequest {

    /**
     * 疾病类型
     */
    private Integer type;

    /**
     * 疾病名称
     */
    private String diseaseName;


    /**
     * 生病时间
     */
    private Date happenTime;
}