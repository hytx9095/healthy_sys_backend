package com.naiyin.healthy.model.dto.doctorDialogue;

import lombok.Data;

import java.io.Serializable;

@Data
public class DoctorDialogueDeleteDTO implements Serializable {

    /**
     * 对话id
     */
    private Long dialogueId;

    private static final long serialVersionUID = 1L;
}