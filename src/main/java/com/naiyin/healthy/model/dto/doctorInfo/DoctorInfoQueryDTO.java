package com.naiyin.healthy.model.dto.doctorInfo;

import com.naiyin.healthy.common.PageRequest;
import lombok.Data;

@Data
public class DoctorInfoQueryDTO extends PageRequest {


    /**
     * 医生类型
     */
    private String type;

    /**
     * 描述
     */
    private String description;

    /**
     * 证明材料
     */
    private String evidence;

    /**
     * 搜索条件
     */
    private String searchCondition;

    /**
     * 医生名称
     */
    private String doctorName;
}