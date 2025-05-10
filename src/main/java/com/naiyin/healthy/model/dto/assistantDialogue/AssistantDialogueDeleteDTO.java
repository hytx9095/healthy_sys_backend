package com.naiyin.healthy.model.dto.assistantDialogue;

import lombok.Data;

import java.io.Serializable;

@Data
public class AssistantDialogueDeleteDTO implements Serializable {

    /**
     * 对话id
     */
    private Long dialogueId;

    private static final long serialVersionUID = 1L;
}