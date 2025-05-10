package com.naiyin.healthy.model.dto.disease;

import lombok.Data;

import java.io.Serializable;

@Data
public class DiseaseInfoDTO implements Serializable {

    /**
     * 疾病类型
     */
    private Integer type;

    /**
     * 疾病名称
     */
    private String diseaseName;

    /**
     * 描述
     */
    private String description;

    /**
     * 生病时间
     */
    private String happenTime;

    private static final long serialVersionUID = 1L;
}