package com.naiyin.healthy.controller;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.naiyin.healthy.common.R;
import com.naiyin.healthy.enums.SysErrorEnum;
import com.naiyin.healthy.exception.CommonException;
import com.naiyin.healthy.model.dto.common.CommonDeleteDTO;
import com.naiyin.healthy.model.dto.dietInfo.DietInfoDTO;
import com.naiyin.healthy.model.dto.dietInfo.DietInfoQueryDTO;
import com.naiyin.healthy.model.dto.dietInfo.DietInfoUpdateDTO;
import com.naiyin.healthy.model.entity.DietInfo;
import com.naiyin.healthy.model.vo.DietInfoVO;
import com.naiyin.healthy.service.DietInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/diet/info")
@RequiredArgsConstructor
@Api(tags = "dietInfo-controller")
public class DietInfoController {

    private final DietInfoService dietInfoService;

    @GetMapping("/get/{dietInfoId}")
    @ApiOperation("获取用户饮食信息")
    public R<DietInfo> getDietInfo(@PathVariable Long dietInfoId) {
        DietInfo dietInfo = dietInfoService.getDietInfo(dietInfoId);
        return R.success(dietInfo);
    }

    @PostMapping("/page")
    @ApiOperation("分页获取用户饮食信息")
    public R<Page<DietInfo>> getDietInfoPage(@RequestBody DietInfoQueryDTO dietInfoDTO) {
        Page<DietInfo> page = dietInfoService.getDietInfoPage(dietInfoDTO);
        return R.success(page);
    }

    @PostMapping("/add")
    @ApiOperation("添加用户饮食信息")
    public R<Boolean> addDietInfo(@RequestBody DietInfoDTO dietInfoDTO) throws ParseException {
        dietInfoService.addDietInfo(dietInfoDTO);
        return R.success(true);
    }

    @PostMapping("/update")
    @ApiOperation("更新用户饮食信息")
    public R<Boolean> updateDietInfo(@RequestBody DietInfoUpdateDTO updateDTO) throws ParseException {
        dietInfoService.updateDietInfo(updateDTO);
        return R.success(true);
    }

    @DeleteMapping()
    @ApiOperation("删除用户饮食信息")
    public R<Boolean> deleteDietInfo(@RequestBody CommonDeleteDTO commonDeleteDTO) {
        boolean result = dietInfoService.removeById(commonDeleteDTO.getId());
        if (!result){
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "删除失败");
        }
        return R.success();
    }
}


