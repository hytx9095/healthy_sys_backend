package com.naiyin.healthy.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.naiyin.healthy.model.dto.suggestions.SuggestionDTO;
import com.naiyin.healthy.model.dto.suggestions.SuggestionQueryDTO;
import com.naiyin.healthy.model.entity.Suggestion;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author wang'ren
* @description 针对表【suggestion】的数据库操作Service
* @createDate 2024-12-09 19:21:26
*/
public interface SuggestionService extends IService<Suggestion> {

    Page<Suggestion> getSuggestions(SuggestionQueryDTO suggestionQueryDTO);

    void updateSuggestion(SuggestionDTO suggestionDTO);

    void generateSuggestion();
}
