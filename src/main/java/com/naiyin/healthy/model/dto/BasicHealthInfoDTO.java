package com.naiyin.healthy.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BasicHealthInfoDTO implements Serializable {

    /**
     * 性别
     */
    private String gender;

    /**
     * 年龄
     */
    private int age;

    /**
     * 身高，单位cm
     */
    private Double height;

    /**
     * 体重，单位kg
     */
    private Double weight;

    /**
     *BMI
     */
    private Double BMI;

    /**
     * 左眼视力
     */
    private Double leftEyeVision;

    /**
     * 右眼视力
     */
    private Double rightEyeVision;

    /**
     * 血压
     */
    private String bloodPressure;

    /**
     * 体温
     */
    private Double bodyTemperature;

    /**
     * 心率
     */
    private Integer heartRate;

    /**
     * 肺活量
     */
    private Integer vitalCapacity;
}