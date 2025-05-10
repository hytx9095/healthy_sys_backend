package com.naiyin.healthy.model.dto.doctorDialogue;

import com.naiyin.healthy.common.PageRequest;
import lombok.Data;

/**
 * 
 * @TableName healthy_knowledge
 */
@Data
public class DoctorDialogueQueryDTO extends PageRequest {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 医生id
     */
    private Long doctorId;

    /**
     * 当前角色
     */
    private String currentRole;

}