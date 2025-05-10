package com.naiyin.healthy.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.naiyin.healthy.common.R;
import com.naiyin.healthy.common.UserContext;
import com.naiyin.healthy.enums.SysErrorEnum;
import com.naiyin.healthy.exception.CommonException;
import com.naiyin.healthy.model.dto.common.CommonDeleteDTO;
import com.naiyin.healthy.model.dto.dietInfo.DietInfoQueryDTO;
import com.naiyin.healthy.model.dto.sportInfo.SportInfoDTO;
import com.naiyin.healthy.model.dto.sportInfo.SportInfoQueryDTO;
import com.naiyin.healthy.model.dto.sportInfo.SportInfoUpdateDTO;
import com.naiyin.healthy.model.entity.DietInfo;
import com.naiyin.healthy.model.entity.SportInfo;
import com.naiyin.healthy.service.SportInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sport/info")
@RequiredArgsConstructor
@Api(tags = "sportInfo-controller")
public class SportInfoController {

    private final SportInfoService sportInfoService;

    @GetMapping("/get/{sportInfoId}")
    @ApiOperation("获取用户运动信息")
    public R<SportInfo> getSportInfo(@PathVariable Long sportInfoId) {
        SportInfo sportInfo = sportInfoService.getSportInfo(sportInfoId);
        return R.success(sportInfo);
    }

    @PostMapping("/page")
    @ApiOperation("分页获取用户运动信息")
    public R<Page<SportInfo>> getSportInfoPage(@RequestBody SportInfoQueryDTO queryDTO) {
        Page<SportInfo> page = sportInfoService.getSportInfoPage(queryDTO);
        return R.success(page);
    }

    @PostMapping("/add")
    @ApiOperation("添加用户运动信息")
    public R<Boolean> addSportInfo(@RequestBody SportInfoDTO sportInfoDTO) throws ParseException {
        sportInfoService.addSportInfo(sportInfoDTO);
        return R.success(true);
    }

    @PostMapping("/update")
    @ApiOperation("更新用户运动信息")
    public R<Boolean> updateSportInfo(@RequestBody SportInfoUpdateDTO updateDTO) throws ParseException {
        sportInfoService.updateSportInfo(updateDTO);
        return R.success(true);
    }

    @DeleteMapping
    @ApiOperation("删除用户运动信息")
    public R<Boolean> deleteSportInfo(@RequestBody CommonDeleteDTO commonDeleteDTO) {
        boolean result = sportInfoService.removeById(commonDeleteDTO.getId());
        if (!result){
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "删除失败");
        }
        return R.success();
    }
    @PostMapping("/generate/suggestions")
    @ApiOperation("生成建议")
    public R<Boolean> generateSuggestions(@RequestBody SportInfoDTO sportInfoDTO) {
        sportInfoService.generateSuggestions();
        return R.success(true);
    }
}


