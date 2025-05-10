package com.naiyin.healthy.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.naiyin.healthy.common.R;
import com.naiyin.healthy.enums.SysErrorEnum;
import com.naiyin.healthy.exception.CommonException;
import com.naiyin.healthy.model.dto.common.CommonDeleteDTO;
import com.naiyin.healthy.model.dto.healthyNews.HealthyNewsDTO;
import com.naiyin.healthy.model.dto.healthyNews.HealthyNewsQueryDTO;
import com.naiyin.healthy.model.dto.healthyNews.HealthyNewsUpdateDTO;
import com.naiyin.healthy.model.entity.HealthyNews;
import com.naiyin.healthy.model.vo.HealthyNewsVO;
import com.naiyin.healthy.service.HealthyNewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/healthy/news")
@RequiredArgsConstructor
@Api(tags = "healthyNews-controller")
public class HealthyNewsController {

    private final HealthyNewsService healthyNewsService;

    @GetMapping("/get/{healthyNewsId}")
    @ApiOperation("获取健康资讯")
    public R<HealthyNews> getHealthyNews(@PathVariable Long healthyNewsId) {
        HealthyNews healthyNews = healthyNewsService.getHealthyNews(healthyNewsId);
        return R.success(healthyNews);
    }

    @PostMapping("/page/vo")
    @ApiOperation("分页获取健康资讯")
    public R<Page<HealthyNewsVO>> getHealthyNewsVOPage(@RequestBody HealthyNewsQueryDTO healthyNewsDTO) {
        Page<HealthyNewsVO> page = healthyNewsService.getHealthyNewsVOPage(healthyNewsDTO);
        return R.success(page);
    }

    @PostMapping("/add")
    @ApiOperation("添加健康资讯")
    public R<Boolean> addHealthyNews(@RequestBody HealthyNewsDTO healthyNewsDTO) throws ParseException {
        healthyNewsService.addHealthyNews(healthyNewsDTO);
        return R.success(true);
    }

    @PostMapping("/update")
    @ApiOperation("更新健康资讯")
    public R<Boolean> updateHealthyNews(@RequestBody HealthyNewsUpdateDTO updateDTO) {
        healthyNewsService.updateHealthyNews(updateDTO);
        return R.success(true);
    }

    @DeleteMapping()
    @ApiOperation("删除健康资讯")
    public R<Boolean> deleteHealthyNews(@RequestBody CommonDeleteDTO commonDeleteDTO) {
        boolean result = healthyNewsService.removeById(commonDeleteDTO.getId());
        if (!result){
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "删除失败");
        }
        return R.success();
    }
}


