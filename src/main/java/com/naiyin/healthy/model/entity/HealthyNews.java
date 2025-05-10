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
 * @TableName healthy_news
 */
@TableName(value ="healthy_news")
@Data
public class HealthyNews implements Serializable {
    /**
     * 健康资讯表主键id
     */
    @TableId
    private Long id;

    /**
     * 上传者id
     */
    private Long userId;

    /**
     * 内容
     */
    private String content;

    /**
     * 是否首页展示
     */
    private Integer isHome;

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