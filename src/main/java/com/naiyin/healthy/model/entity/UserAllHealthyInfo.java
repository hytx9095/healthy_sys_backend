package com.naiyin.healthy.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserAllHealthyInfo implements Serializable {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户基本健康信息
     */
    private BasicHealthInfo basicHealthInfo;

    /**
     * 用户饮食信息
     */
    private List<DietInfo> dietInfos;

    /**
     * 用户运动信息
     */
    private List<SportInfo> sportInfos;

    /**
     * 用户疾病信息
     */
    private List<DiseaseInfo> diseaseInfos;
}
