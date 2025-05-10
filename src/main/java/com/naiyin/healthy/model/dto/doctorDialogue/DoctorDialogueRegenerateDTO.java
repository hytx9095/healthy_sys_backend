package com.naiyin.healthy.model.dto.doctorDialogue;

import lombok.Data;

import java.io.Serializable;

@Data
public class DoctorDialogueRegenerateDTO implements Serializable {

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