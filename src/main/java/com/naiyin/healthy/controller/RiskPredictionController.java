package com.naiyin.healthy.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.naiyin.healthy.common.R;
import com.naiyin.healthy.common.UserContext;
import com.naiyin.healthy.model.dto.RiskPrediction.RiskPredictionDTO;
import com.naiyin.healthy.model.dto.RiskPrediction.RiskPredictionQueryDTO;
import com.naiyin.healthy.model.entity.DietInfo;
import com.naiyin.healthy.model.entity.RiskPrediction;
import com.naiyin.healthy.service.RiskPredictionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/riskPrediction")
@RequiredArgsConstructor
@Api(tags = "riskPrediction-controller")
public class RiskPredictionController {

    private final RiskPredictionService riskPredictionService;

    @GetMapping("/get")
    @ApiOperation("获取风险分析")
    public R<RiskPrediction> getRiskPrediction() {
        RiskPrediction riskPrediction = riskPredictionService.getRiskPrediction();
        return R.success(riskPrediction);
    }

    @PostMapping("/page")
    @ApiOperation("分页获取用户饮食信息")
    public R<Page<RiskPrediction>> getRiskPredictionPage(@RequestBody RiskPredictionQueryDTO riskPredictionQueryDTO) {
        Page<RiskPrediction> page = riskPredictionService.getRiskPredictionPage(riskPredictionQueryDTO);
        return R.success(page);
    }

    @PostMapping("/generate")
    @ApiOperation("添加风险分析")
    public R<Boolean> generateRiskPrediction() {
        riskPredictionService.generateRiskPrediction();
        return R.success(true);
    }

    @PostMapping("/update")
    @ApiOperation("更新风险分析")
    public R<Boolean> updateRiskPrediction(@RequestBody RiskPredictionDTO riskPredictionDTO) {
        riskPredictionService.updateRiskPrediction(riskPredictionDTO);
        return R.success(true);
    }

    @DeleteMapping
    @ApiOperation("删除风险分析")
    public R<Boolean> deleteRiskPrediction() {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", UserContext.getUserId());
        boolean result = riskPredictionService.removeByMap(map);
        return R.success(result);
    }
}


