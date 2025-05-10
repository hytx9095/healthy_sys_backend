package com.naiyin.healthy.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.naiyin.healthy.common.R;
import com.naiyin.healthy.enums.SysErrorEnum;
import com.naiyin.healthy.exception.CommonException;
import com.naiyin.healthy.model.dto.common.CommonDeleteDTO;
import com.naiyin.healthy.model.dto.disease.DiseaseInfoDTO;
import com.naiyin.healthy.model.dto.disease.DiseaseInfoQueryDTO;
import com.naiyin.healthy.model.dto.disease.DiseaseInfoUpdateDTO;
import com.naiyin.healthy.model.entity.DiseaseInfo;
import com.naiyin.healthy.service.DiseaseInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/disease/info")
@RequiredArgsConstructor
@Api(tags = "diseaseInfo-controller")
public class DiseaseInfoController {

    private final DiseaseInfoService diseaseInfoService;

    @GetMapping("/get/{DiseaseInfoId}")
    @ApiOperation("获取用户疾病信息")
    public R<DiseaseInfo> getDiseaseInfo(@PathVariable Long DiseaseInfoId) {
        DiseaseInfo DiseaseInfo = diseaseInfoService.getDiseaseInfo(DiseaseInfoId);
        return R.success(DiseaseInfo);
    }

    @PostMapping("/page")
    @ApiOperation("分页获取用户疾病信息")
    public R<Page<DiseaseInfo>> getDiseaseInfoPage(@RequestBody DiseaseInfoQueryDTO DiseaseInfoDTO) {
        Page<DiseaseInfo> page = diseaseInfoService.getDiseaseInfoPage(DiseaseInfoDTO);
        return R.success(page);
    }

    @PostMapping("/add")
    @ApiOperation("添加用户疾病信息")
    public R<Boolean> addDiseaseInfo(@RequestBody DiseaseInfoDTO DiseaseInfoDTO) throws ParseException {
        diseaseInfoService.addDiseaseInfo(DiseaseInfoDTO);
        return R.success(true);
    }

    @PostMapping("/update")
    @ApiOperation("更新用户疾病信息")
    public R<Boolean> updateDiseaseInfo(@RequestBody DiseaseInfoUpdateDTO updateDTO) throws ParseException {
        diseaseInfoService.updateDiseaseInfo(updateDTO);
        return R.success(true);
    }

    @DeleteMapping()
    @ApiOperation("删除用户疾病信息")
    public R<Boolean> deleteDiseaseInfo(@RequestBody CommonDeleteDTO commonDeleteDTO) {
        boolean result = diseaseInfoService.removeById(commonDeleteDTO.getId());
        if (!result){
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "删除失败");
        }
        return R.success();
    }
}


