package com.naiyin.healthy.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * @TableName suggestion
 */
@TableName(value ="suggestion")
@Data
public class Suggestion extends Base {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 建议标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 是否阅读
     */
    private Integer isRead;

}