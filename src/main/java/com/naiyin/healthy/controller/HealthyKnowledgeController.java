package com.naiyin.healthy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.naiyin.healthy.common.R;
import com.naiyin.healthy.common.UserContext;
import com.naiyin.healthy.model.dto.healthyKnowledge.*;
import com.naiyin.healthy.model.entity.HealthyKnowledge;
import com.naiyin.healthy.model.entity.HealthyKnowledgeStar;
import com.naiyin.healthy.model.vo.HealthyKnowledgeVO;
import com.naiyin.healthy.service.HealthyKnowledgeExamineService;
import com.naiyin.healthy.service.HealthyKnowledgeService;
import com.naiyin.healthy.service.HealthyKnowledgeStarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/healthy/knowledge")
@RequiredArgsConstructor
@Api(tags = "healthyKnowledge-controller")
public class HealthyKnowledgeController {

    private final HealthyKnowledgeService healthyKnowledgeService;
    private final HealthyKnowledgeExamineService healthyKnowledgeExamineService;

    @GetMapping("/get")
    @ApiOperation("获取健康知识")
    public R<HealthyKnowledge> getHealthyKnowledge() {
        HealthyKnowledge healthyKnowledge = healthyKnowledgeService.getHealthyKnowledge();
        return R.success(healthyKnowledge);
    }

    /**
     * 分页获取健康知识
     */
    @PostMapping("/page/vo")
    @ApiOperation("获取健康知识（分页）")
    public R<Page<HealthyKnowledgeVO>> getHealthyKnowledgeVOPage(@RequestBody HealthyKnowledgeQueryDTO healthyKnowledgeQueryDTO) {
        Page<HealthyKnowledgeVO> healthyKnowledgePage = healthyKnowledgeService.getHealthyKnowledgePage(healthyKnowledgeQueryDTO);
        return R.success(healthyKnowledgePage);
    }

    /**
     * 分页获取健康知识
     */
    @PostMapping("/home/page/vo")
    @ApiOperation("获取首页健康知识（分页）")
    public R<List<HealthyKnowledgeVO>> getHomeHealthyKnowledgeVOPage(@RequestBody HealthyKnowledgeQueryDTO healthyKnowledgeQueryDTO) {
        List<HealthyKnowledgeVO> homeHealthyKnowledgePage = healthyKnowledgeService.getHomeHealthyKnowledgePage(healthyKnowledgeQueryDTO);
        return R.success(homeHealthyKnowledgePage);
    }

    /**
     * 分页获取健康知识
     */
    @PostMapping("/page/vo/star")
    @ApiOperation("获取健康知识（分页）")
    public R<IPage<HealthyKnowledgeVO>> getStarHealthyKnowledgeVOPage(@RequestBody HealthyKnowledgeQueryDTO healthyKnowledgeQueryDTO) {
        IPage<HealthyKnowledgeVO> healthyKnowledgePage = healthyKnowledgeService.getStarHealthyKnowledgePage(healthyKnowledgeQueryDTO);
        return R.success(healthyKnowledgePage);
    }

    /**
     * 分页获取健康知识
     */
    @PostMapping("/page/vo/my")
    @ApiOperation("获取健康知识（分页）")
    public R<IPage<HealthyKnowledgeVO>> getStarHealthyKnowledgeVOPageById(@RequestBody HealthyKnowledgeQueryDTO healthyKnowledgeQueryDTO) {
        IPage<HealthyKnowledgeVO> healthyKnowledgePage = healthyKnowledgeService.getStarHealthyKnowledgePageById(healthyKnowledgeQueryDTO);
        return R.success(healthyKnowledgePage);
    }

    /**
     * 分页获取健康知识
     */
    @PostMapping("/page/vo/starred")
    @ApiOperation("获取健康知识（分页）")
    public R<IPage<HealthyKnowledgeVO>> getStarredHealthyKnowledgeVOPage(@RequestBody HealthyKnowledgeQueryDTO healthyKnowledgeQueryDTO) {
        IPage<HealthyKnowledgeVO> healthyKnowledgePage = healthyKnowledgeService.getStarredHealthyKnowledgePage(healthyKnowledgeQueryDTO);
        return R.success(healthyKnowledgePage);
    }

    /**
     * 添加健康知识
     */
    @PostMapping("/share")
    @ApiOperation("用户分享健康知识")
    public R<Boolean> shareHealthyKnowledge(@RequestBody HealthyKnowledgeDTO healthyKnowledgeDTO) {
        healthyKnowledgeService.shareHealthyKnowledge(healthyKnowledgeDTO);
        return R.success(true);
    }

    /**
     * 审核健康知识（管理员）
     */
    @PostMapping("/examine")
    @ApiOperation("审核健康知识")
    public R<Boolean> examineHealthyKnowledge(@RequestBody HealthyKnowledgeExamineDTO healthyKnowledgeExamineDTO) {
        healthyKnowledgeExamineService.examineHealthyKnowledge(healthyKnowledgeExamineDTO);
        return R.success(true);
    }

    /**
     * 添加健康知识（管理员）
     */
    @PostMapping("/add")
    @ApiOperation("添加健康知识")
    public R<Boolean> addHealthyKnowledge(@RequestBody HealthyKnowledgeDTO healthyKnowledgeDTO) {
        healthyKnowledgeService.addHealthyKnowledge(healthyKnowledgeDTO);
        return R.success(true);
    }

    /**
     * 批量添加健康知识（管理员）
     */
    @PostMapping("/add/list")
    @ApiOperation("批量添加健康知识")
    public R<Boolean> addHealthyKnowledgeList(@RequestBody HealthyKnowledgeListDTO healthyKnowledgeListDTO) {
        healthyKnowledgeService.addHealthyKnowledgeList(healthyKnowledgeListDTO);
        return R.success(true);
    }

    /**
     * 批量添加健康知识（管理员）
     */
    @PostMapping("/add/excel")
    @ApiOperation("批量添加健康知识（excel)")
    public R<Boolean> addHealthyKnowledgeByExcel(@RequestBody HealthyKnowledgeListDTO healthyKnowledgeListDTO) {
        // TODO excel添加健康知识
        return R.success(true);
    }

    @PostMapping("/update")
    @ApiOperation("更新健康知识")
    public R<Boolean> updateHealthyKnowledge(@RequestBody HealthyKnowledgeDTO healthyKnowledgeDTO) {
        healthyKnowledgeService.updateHealthyKnowledge(healthyKnowledgeDTO);
        return R.success(true);
    }

    @DeleteMapping
    @ApiOperation("删除健康知识")
    public R<Boolean> deleteHealthyKnowledge(@RequestBody HealthyKnowledgeDeleteDTO healthyKnowledgeDeleteDTO) {
        boolean result = healthyKnowledgeService.removeById(healthyKnowledgeDeleteDTO.getId());
        if (!result) {
            return R.error("删除失败");
        }
        return R.success(true);
    }

    @PostMapping("/pass/{healthyKnowledgeId}")
    @ApiOperation("通过用户分享的健康知识")
    public R<Boolean> passHealthyKnowledge(@PathVariable Long healthyKnowledgeId) {
        return R.success(true);
    }

    @PostMapping("/star")
    @ApiOperation("收藏健康知识")
    public R<Boolean> starHealthyKnowledge(@RequestBody HealthyKnowledgeStarDTO healthyKnowledgeStarDTO) {
        healthyKnowledgeService.starHealthyKnowledge(healthyKnowledgeStarDTO);
        return R.success(true);
    }

    @PostMapping("/star/cancel")
    @ApiOperation("取消收藏健康知识")
    public R<Boolean> cancelStarHealthyKnowledge(@RequestBody HealthyKnowledgeStarDTO healthyKnowledgeStarDTO) {
        healthyKnowledgeService.cancelHealthyKnowledge(healthyKnowledgeStarDTO);
        return R.success(true);
    }
}


