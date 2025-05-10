package com.naiyin.healthy.model.dto.assistantDialogue;

import lombok.Data;

import java.io.Serializable;

@Data
public class AssistantDialogueRegenerateDTO implements Serializable {

    /**
     * 问题对话id
     */
    private Long questionId;

    /**
     * 答案对话id
     */
    private Long answerId;

    private static final long serialVersionUID = 1L;
}