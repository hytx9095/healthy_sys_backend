package com.naiyin.healthy.model.dto.doctorInfo;

import com.naiyin.healthy.common.PageRequest;
import lombok.Data;

@Data
public class GetDoctorInfoDTO extends PageRequest {


    /**
     * id
     */
    private Long userId;

}