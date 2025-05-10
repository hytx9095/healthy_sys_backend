package com.naiyin.healthy.model.dto.RiskPrediction;

import com.naiyin.healthy.common.PageRequest;
import lombok.Data;

@Data
public class RiskPredictionQueryDTO extends PageRequest {

    /**
     * 是否阅读
     */
    private Integer isRead;
}