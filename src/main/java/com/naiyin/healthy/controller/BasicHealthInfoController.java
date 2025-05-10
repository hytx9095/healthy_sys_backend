package com.naiyin.healthy.controller;

import com.naiyin.healthy.common.R;
import com.naiyin.healthy.common.UserContext;
import com.naiyin.healthy.enums.SysErrorEnum;
import com.naiyin.healthy.exception.CommonException;
import com.naiyin.healthy.model.dto.BasicHealthInfoDTO;
import com.naiyin.healthy.model.dto.common.CommonDeleteDTO;
import com.naiyin.healthy.model.entity.BasicHealthInfo;
import com.naiyin.healthy.service.BasicHealthInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/basic/health/info")
@RequiredArgsConstructor
@Api(tags = "basicHealthInfo-controller")
public class BasicHealthInfoController {

    private final BasicHealthInfoService basicHealthInfoService;

    @GetMapping("/get")
    @ApiOperation("获取用户基本健康信息")
    public R<BasicHealthInfo> getBasicHealthInfo() {
        BasicHealthInfo basicHealthInfo = basicHealthInfoService.getBasicHealthInfo();
        return R.success(basicHealthInfo);
    }

    @PostMapping("/add")
    @ApiOperation("添加用户基本健康信息")
    public R<Boolean> addBasicHealthInfo(@RequestBody BasicHealthInfoDTO basicHealthInfoDTO) {
        basicHealthInfoService.addBasicHealthInfo(basicHealthInfoDTO);
        return R.success(true);
    }

    @PostMapping("/update")
    @ApiOperation("更新用户基本健康信息")
    public R<Boolean> updateBasicHealthInfo(@RequestBody BasicHealthInfoDTO basicHealthInfoDTO) {
        basicHealthInfoService.updateBasicHealthInfo(basicHealthInfoDTO);
        return R.success(true);
    }

    @DeleteMapping
    @ApiOperation("删除用户基本信息")
    public R<Boolean> deleteBasicHealthyInfo(@RequestBody CommonDeleteDTO commonDeleteDTO) {
        boolean result = basicHealthInfoService.removeById(commonDeleteDTO.getId());
        if (!result){
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "删除失败");
        }
        return R.success();
    }
}


