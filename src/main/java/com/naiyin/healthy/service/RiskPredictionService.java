package com.naiyin.healthy.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.naiyin.healthy.model.dto.RiskPrediction.RiskPredictionDTO;
import com.naiyin.healthy.model.dto.RiskPrediction.RiskPredictionQueryDTO;
import com.naiyin.healthy.model.entity.RiskPrediction;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author wang'ren
* @description 针对表【risk_prediction】的数据库操作Service
* @createDate 2024-12-09 19:21:20
*/
public interface RiskPredictionService extends IService<RiskPrediction> {
    RiskPrediction getRiskPrediction();

    void updateRiskPrediction(RiskPredictionDTO riskPredictionDTO);

    void addRiskPrediction(RiskPredictionDTO riskPredictionDTO);

    void generateRiskPrediction();

    Page<RiskPrediction> getRiskPredictionPage(RiskPredictionQueryDTO riskPredictionQueryDTO);
}
