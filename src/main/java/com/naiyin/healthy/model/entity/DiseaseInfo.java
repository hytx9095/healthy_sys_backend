package com.naiyin.healthy.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName disease_info
 */
@TableName(value ="disease_info")
@Data
public class DiseaseInfo extends Base {

    /**
     * 用户id
     */
    private Long userId;

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
    private Date happenTime;
}