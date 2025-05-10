package com.naiyin.healthy.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * @TableName basic_health_info
 */
@TableName(value ="basic_health_info")
@Data
public class BasicHealthInfo extends Base{

    /**
     * 用户id
     */
    private Long userId;

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