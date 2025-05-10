package com.naiyin.healthy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.naiyin.healthy.model.dto.question.QuestionDTO;
import com.naiyin.healthy.model.dto.question.QuestionListDTO;
import com.naiyin.healthy.model.dto.question.QuestionQueryDTO;
import com.naiyin.healthy.model.entity.Question;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author wang'ren
* @description 针对表【question】的数据库操作Service
* @createDate 2024-12-17 11:22:49
*/
public interface QuestionService extends IService<Question> {

    Question getQuestion(Long questionId);

    void updateQuestion(QuestionDTO questionDTO);

    void addQuestion(QuestionDTO questionDTO);

    Page<Question> getQuestionPage(QuestionQueryDTO queryDTO);

    QueryWrapper<Question> getQueryWrapper(QuestionQueryDTO queryDTO);

    void addQuestionList(QuestionListDTO listDTO);
}
