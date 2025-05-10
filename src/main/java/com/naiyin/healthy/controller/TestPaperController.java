package com.naiyin.healthy.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.naiyin.healthy.common.R;
import com.naiyin.healthy.model.dto.testPaper.TestPaperDTO;
import com.naiyin.healthy.model.dto.testPaper.TestPaperQueryDTO;
import com.naiyin.healthy.model.entity.TestPaper;
import com.naiyin.healthy.service.TestPaperService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/testPaper")
@RequiredArgsConstructor
@Api(tags = "testPaper-controller")
public class TestPaperController {

    private final TestPaperService testPaperService;

    @GetMapping("/get/{testPaperId}")
    @ApiOperation("获取试卷")
    public R<TestPaper> getTestPaper(@PathVariable Long testPaperId) {
        TestPaper testPaper = testPaperService.getTestPaper(testPaperId);
        return R.success(testPaper);
    }

    @PostMapping("/page")
    @ApiOperation("获取试卷（分页）")
    public R<Page<TestPaper>> getTestPaperPage(@RequestBody TestPaperQueryDTO queryDTO) {
        Page<TestPaper> page = testPaperService.getTestPaperPage(queryDTO);
        return R.success(page);
    }

    @PostMapping("/generate")
    @ApiOperation("生成试卷")
    public R<Boolean>generateTestPaper() {
        testPaperService.generateTestPaper();
        return R.success(true);
    }

    @PostMapping("/answer")
    @ApiOperation("更新试卷")
    public R<Boolean>answerTestPaper(@RequestBody TestPaperDTO testPaperDTO) {
        testPaperService.answerTestPaper(testPaperDTO);
        return R.success(true);
    }

    @DeleteMapping("{testPaperId}")
    @ApiOperation("删除试卷")
    public R<Boolean> deleteTestPaper(@PathVariable String testPaperId) {
        boolean result = testPaperService.removeById(testPaperId);
        return R.success(result);
    }
}


