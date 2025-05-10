package com.naiyin.healthy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.naiyin.healthy.model.dto.testPaper.TestPaperDTO;
import com.naiyin.healthy.model.dto.testPaper.TestPaperQueryDTO;
import com.naiyin.healthy.model.entity.TestPaper;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author wang'ren
* @description 针对表【test_paper】的数据库操作Service
* @createDate 2024-12-17 11:22:57
*/
public interface TestPaperService extends IService<TestPaper> {

    TestPaper getTestPaper(Long testPaperId);

    void answerTestPaper(TestPaperDTO testPaperDTO);

    void generateTestPaper();

    Page<TestPaper> getTestPaperPage(TestPaperQueryDTO queryDTO);

    QueryWrapper<TestPaper> getQueryWrapper(TestPaperQueryDTO queryDTO);
}
