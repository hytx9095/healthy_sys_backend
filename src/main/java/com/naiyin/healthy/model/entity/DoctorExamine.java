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
 * @TableName doctor_examine
 */
@TableName(value ="doctor_examine")
@Data
public class DoctorExamine implements Serializable {
    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 医生信息id
     */
    private Long doctorInfoId;

    /**
     * 审核说明
     */
    private String description;

    /**
     * 是否通过，0：没通过、1：通过
     */
    private Integer result;

    /**
     * 操作时间
     */
    private Date operationTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否删除
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}