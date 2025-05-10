package com.naiyin.healthy.model.dto.assistantDialogue;

import com.naiyin.healthy.common.PageRequest;
import lombok.Data;

/**
 * 
 * @TableName healthy_knowledge
 */
@Data
public class AssistantDialogueQueryDTO extends PageRequest {

    /**
     * 用户id
     */
    private Long userId;

}