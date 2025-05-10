package com.naiyin.healthy.model.entity;

import com.naiyin.healthy.model.dto.RiskPrediction.RiskPredictionDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RiskPredictionMessage implements Serializable {

    private List<RiskPredictionDTO> riskPredictionDTOList;
}
