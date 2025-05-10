package com.naiyin.healthy.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * @TableName assistant_dialogue
 */
@TableName(value ="assistant_dialogue")
@Data
public class AssistantDialogue extends Base {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 发言人
     */
    private String spokesman;

    /**
     * 内容
     */
    private String content;

    /**
     * 状态
     */
    private Integer status;

}