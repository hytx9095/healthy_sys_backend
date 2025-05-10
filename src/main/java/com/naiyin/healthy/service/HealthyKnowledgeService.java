package com.naiyin.healthy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.naiyin.healthy.model.dto.healthyKnowledge.HealthyKnowledgeStarDTO;
import com.naiyin.healthy.model.dto.suggestions.SuggestionDTO;
import com.naiyin.healthy.model.dto.healthyKnowledge.HealthyKnowledgeDTO;
import com.naiyin.healthy.model.dto.healthyKnowledge.HealthyKnowledgeListDTO;
import com.naiyin.healthy.model.dto.healthyKnowledge.HealthyKnowledgeQueryDTO;
import com.naiyin.healthy.model.entity.HealthyKnowledge;
import com.baomidou.mybatisplus.extension.service.IService;
import com.naiyin.healthy.model.vo.HealthyKnowledgeVO;

import java.util.List;

/**
* @author wang'ren
* @description 针对表【healthy_knowledge】的数据库操作Service
* @createDate 2024-12-09 17:05:30
*/
public interface HealthyKnowledgeService extends IService<HealthyKnowledge> {
    
    HealthyKnowledge getHealthyKnowledge();

    void updateHealthyKnowledge(HealthyKnowledgeDTO healthyKnowledgeDTO);

    void addHealthyKnowledge(HealthyKnowledgeDTO healthyKnowledgeDTO);

    IPage<HealthyKnowledgeVO> getStarHealthyKnowledgePage(HealthyKnowledgeQueryDTO healthyKnowledgeQueryDTO);
    IPage<HealthyKnowledgeVO> getStarredHealthyKnowledgePage(HealthyKnowledgeQueryDTO healthyKnowledgeQueryDTO);


    Page<HealthyKnowledgeVO> getHealthyKnowledgePage(HealthyKnowledgeQueryDTO healthyKnowledgeQueryDTO);

    List<HealthyKnowledgeVO> getHomeHealthyKnowledgePage(HealthyKnowledgeQueryDTO healthyKnowledgeQueryDTO);

    QueryWrapper<HealthyKnowledge> getQueryWrapper(HealthyKnowledgeQueryDTO healthyKnowledgeQueryDTO);

    void addHealthyKnowledgeList(HealthyKnowledgeListDTO healthyKnowledgeListDTO);

    List<HealthyKnowledge> getKnowledgeListByType(SuggestionDTO suggestionDTO);

    void shareHealthyKnowledge(HealthyKnowledgeDTO healthyKnowledgeDTO);

    void starHealthyKnowledge(HealthyKnowledgeStarDTO healthyKnowledgeStarDTO);

    void cancelHealthyKnowledge(HealthyKnowledgeStarDTO healthyKnowledgeStarDTO);
}
