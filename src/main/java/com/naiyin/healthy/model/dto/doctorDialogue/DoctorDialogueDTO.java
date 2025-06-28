package com.naiyin.healthy.model.dto.doctorDialogue;

import lombok.Data;

import java.io.Serializable;

@Data
public class DoctorDialogueDTO implements Serializable {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 医生id
     */
    private Long doctorId;
    /**
     * 内容
     */
    private String content;

    private static final long serialVersionUID = 1L;

}