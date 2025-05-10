package com.naiyin.healthy.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.naiyin.healthy.common.UserContext;
import com.naiyin.healthy.constant.CommonConstant;
import com.naiyin.healthy.constant.TestPageConstant;
import com.naiyin.healthy.enums.SysErrorEnum;
import com.naiyin.healthy.exception.CommonException;
import com.naiyin.healthy.model.dto.testPaper.TestPaperDTO;
import com.naiyin.healthy.model.dto.testPaper.TestPaperQueryDTO;
import com.naiyin.healthy.model.entity.Question;
import com.naiyin.healthy.model.entity.TestPaper;
import com.naiyin.healthy.service.QuestionService;
import com.naiyin.healthy.service.TestPaperService;
import com.naiyin.healthy.mapper.TestPaperMapper;
import com.naiyin.healthy.util.SqlUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author wang'ren
* @description 针对表【test_paper】的数据库操作Service实现
* @createDate 2024-12-17 11:22:57
*/
@Service
public class TestPaperServiceImpl extends ServiceImpl<TestPaperMapper, TestPaper>
    implements TestPaperService{

    @Resource
    private QuestionService questionService;

    @Override
    public TestPaper getTestPaper(Long testPaperId) {
        TestPaper testPaper = lambdaQuery()
                .eq(TestPaper::getId, testPaperId)
                .one();
        if (testPaper == null){
            throw new CommonException(SysErrorEnum.NOT_FOUND_ERROR, "试卷不存在");
        }
        return testPaper;
    }

    @Override
    public void answerTestPaper(TestPaperDTO testPaperDTO) {
        TestPaper testPaper = lambdaQuery().eq(TestPaper::getId, testPaperDTO.getTeatPaperId()).one();
        Map<Integer, String> userAnswerMap = testPaperDTO.getUserAnswer();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<Integer, String> answerMap;
        try {
            answerMap = objectMapper.readValue(testPaper.getAnswer(), HashMap.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        //判题
        double score = 0;
        for (int i = 0; i < answerMap.size(); i++) {
            if (userAnswerMap.get(i).equals(answerMap.get(String.valueOf(i)))){
                score += TestPageConstant.FULL_MARKS / answerMap.size();
            }
        }
        testPaper.setId(testPaperDTO.getTeatPaperId());
        testPaper.setUserAnswer(JSONUtil.toJsonStr(userAnswerMap));
        testPaper.setScore(score);
        boolean result = updateById(testPaper);
        if (!result){
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "更新失败");
        }
    }

    @Override
    public void generateTestPaper() {
        List<Question> questionList = questionService.lambdaQuery().eq(Question::getIsDelete, 0).list();
        List<Question> testPaperQuestions = RandomUtil.randomEleList(questionList, TestPageConstant.EASY_LEVEL);
        Map<Integer, Long> questions = new HashMap<>();
        Map<Integer, String> answers = new HashMap<>();
        for (int i = 0; i < testPaperQuestions.size(); i++) {
            questions.put(i, testPaperQuestions.get(i).getId());
            answers.put(i, testPaperQuestions.get(i).getAnswer());
        }
        TestPaper testPaper = new TestPaper();
        testPaper.setUserId(UserContext.getUserId());
        testPaper.setQuestions(JSONUtil.toJsonStr(questions));
        testPaper.setAnswer(JSONUtil.toJsonStr(answers));
        boolean result = save(testPaper);
        if (!result){
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "添加失败");
        }
    }

    @Override
    public Page<TestPaper> getTestPaperPage(TestPaperQueryDTO queryDTO) {
        Page<TestPaper> page = page(new Page<>(queryDTO.getCurrent(), queryDTO.getPageSize())
                , getQueryWrapper(queryDTO));
        return page;
    }

    @Override
    public QueryWrapper<TestPaper> getQueryWrapper(TestPaperQueryDTO queryDTO) {
        if (ObjectUtil.isNull(queryDTO)){
            throw new CommonException(SysErrorEnum.PARAM_ERROR, "请求参数为空");
        }
        Double score = queryDTO.getScore();
        String sortField = queryDTO.getSortField();
        String sortOrder = queryDTO.getSortOrder();
        QueryWrapper<TestPaper> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(score != null, "score", score);
        queryWrapper.eq("user_id", UserContext.getUserId());
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }
}




