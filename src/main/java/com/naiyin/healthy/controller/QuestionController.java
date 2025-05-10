package com.naiyin.healthy.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.naiyin.healthy.common.R;
import com.naiyin.healthy.common.UserContext;
import com.naiyin.healthy.model.dto.healthyKnowledge.HealthyKnowledgeListDTO;
import com.naiyin.healthy.model.dto.question.QuestionDTO;
import com.naiyin.healthy.model.dto.question.QuestionListDTO;
import com.naiyin.healthy.model.dto.question.QuestionQueryDTO;
import com.naiyin.healthy.model.entity.Question;
import com.naiyin.healthy.service.QuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
@Api(tags = "question-controller")
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/get/{questionId}")
    @ApiOperation("获取题目")
    public R<Question> getQuestion(@PathVariable Long questionId) {
        Question question = questionService.getQuestion(questionId);
        return R.success(question);
    }

    @PostMapping("/page")
    @ApiOperation("获取试卷（分页）")
    public R<Page<Question>> getQuestionPage(@RequestBody QuestionQueryDTO queryDTO) {
        Page<Question> page = questionService.getQuestionPage(queryDTO);
        return R.success(page);
    }

    @PostMapping("/add")
    @ApiOperation("添加题目")
    public R<Boolean> addQuestion(@RequestBody QuestionDTO questionDTO) {
        questionService.addQuestion(questionDTO);
        return R.success(true);
    }

    @PostMapping("/add/list")
    @ApiOperation("批量添加题目")
    public R<Boolean> addHealthyKnowledgeList(@RequestBody QuestionListDTO listDTO) {
        questionService.addQuestionList(listDTO);
        return R.success(true);
    }

    @PostMapping("/update")
    @ApiOperation("更新题目")
    public R<Boolean> updateQuestion(@RequestBody QuestionDTO questionDTO) {
        questionService.updateQuestion(questionDTO);
        return R.success(true);
    }

    @DeleteMapping("{questionId}")
    @ApiOperation("删除题目")
    public R<Boolean> deleteQuestion(@PathVariable String questionId) {
        boolean result = questionService.removeById(questionId);
        return R.success(result);
    }
}


