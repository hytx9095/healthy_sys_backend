package com.naiyin.healthy.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * @TableName user_tags
 */
@TableName(value ="user_tags")
@Data
public class UserTags extends Base {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 标签
     */
    private String tags;

}