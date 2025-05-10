package com.naiyin.healthy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.naiyin.healthy.common.UserContext;
import com.naiyin.healthy.constant.CommonConstant;
import com.naiyin.healthy.enums.SysErrorEnum;
import com.naiyin.healthy.exception.CommonException;
import com.naiyin.healthy.model.dto.question.QuestionDTO;
import com.naiyin.healthy.model.dto.question.QuestionListDTO;
import com.naiyin.healthy.model.dto.question.QuestionQueryDTO;
import com.naiyin.healthy.model.entity.Question;
import com.naiyin.healthy.service.QuestionService;
import com.naiyin.healthy.mapper.QuestionMapper;
import com.naiyin.healthy.util.SqlUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author wang'ren
* @description 针对表【question】的数据库操作Service实现
* @createDate 2024-12-17 11:22:49
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{

    @Override
    public Question getQuestion(Long questionId) {
        Question question = lambdaQuery()
                .eq(Question::getId, questionId)
                .one();
        if (question == null){
            throw new CommonException(SysErrorEnum.NOT_FOUND_ERROR, "问题不存在");
        }
        return question;
    }

    @Override
    public void updateQuestion(QuestionDTO questionDTO) {
        Question question = new Question();
        question.setUserId(UserContext.getUserId());
        BeanUtil.copyProperties(questionDTO, question);
        boolean result = updateById(question);
        if (!result){
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "更新失败");
        }
    }

    @Override
    public void addQuestion(QuestionDTO questionDTO) {
        Question question = BeanUtil.copyProperties(questionDTO, Question.class);
        question.setUserId(UserContext.getUserId());
        question.setWrongAnswers(JSONUtil.toJsonStr(questionDTO.getWrongAnswers()));
        boolean result = save(question);
        if (!result){
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "添加失败");
        }
    }

    @Override
    public Page<Question> getQuestionPage(QuestionQueryDTO queryDTO) {
        Page<Question> page = page(new Page<>(queryDTO.getCurrent(), queryDTO.getPageSize())
                , getQueryWrapper(queryDTO));
        return page;
    }

    @Override
    public QueryWrapper<Question> getQueryWrapper(QuestionQueryDTO queryDTO) {
        if (ObjectUtil.isNull(queryDTO)){
            throw new CommonException(SysErrorEnum.PARAM_ERROR, "请求参数为空");
        }
        String title = queryDTO.getTitle();
        Integer type = queryDTO.getType();
        String sortField = queryDTO.getSortField();
        String sortOrder = queryDTO.getSortOrder();
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotBlank(title), "title", title);
        queryWrapper.eq(type != null, "type", type);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public void addQuestionList(QuestionListDTO listDTO) {
        if (ObjectUtil.isNull(listDTO)){
            throw new CommonException(SysErrorEnum.PARAM_ERROR, "请求参数为空");
        }
        List<QuestionDTO> questionList = listDTO.getQuestionList();
        for (QuestionDTO questionDTO : questionList) {
            addQuestion(questionDTO);
        }
    }
}




