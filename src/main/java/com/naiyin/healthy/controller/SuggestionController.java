package com.naiyin.healthy.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.naiyin.healthy.common.R;
import com.naiyin.healthy.common.UserContext;
import com.naiyin.healthy.model.dto.suggestions.SuggestionDTO;
import com.naiyin.healthy.model.dto.suggestions.SuggestionQueryDTO;
import com.naiyin.healthy.model.entity.Suggestion;
import com.naiyin.healthy.service.SuggestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/suggestion")
@RequiredArgsConstructor
@Api(tags = "suggestion-controller")
public class SuggestionController {

    private final SuggestionService suggestionService;

    @PostMapping("/page")
    @ApiOperation("获取智能建议（分页）")
    public R<Page<Suggestion>> getSuggestions(@RequestBody SuggestionQueryDTO suggestionQueryDTO) {
        Page<Suggestion> page = suggestionService.getSuggestions(suggestionQueryDTO);
        return R.success(page);
    }

    @PostMapping("/generate")
    @ApiOperation("生成智能建议")
    public R<Boolean> generateSuggestion() {
        suggestionService.generateSuggestion();
        return R.success(true);
    }

    @PostMapping("/update")
    @ApiOperation("更新智能建议")
    public R<Boolean> updateSuggestion(@RequestBody SuggestionDTO suggestionDTO) {
        suggestionService.updateSuggestion(suggestionDTO);
        return R.success(true);
    }

    @DeleteMapping
    @ApiOperation("删除智能建议")
    public R<Boolean> deleteSuggestion() {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", UserContext.getUserId());
        boolean result = suggestionService.removeByMap(map);
        return R.success(result);
    }
}


