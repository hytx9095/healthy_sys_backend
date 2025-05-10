package com.naiyin.healthy.service;

import com.naiyin.healthy.model.dto.healthyKnowledge.HealthyKnowledgeExamineDTO;
import com.naiyin.healthy.model.entity.HealthyKnowledgeExamine;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author wang'ren
* @description 针对表【healthy_knowledge_examine】的数据库操作Service
* @createDate 2025-04-13 13:21:13
*/
public interface HealthyKnowledgeExamineService extends IService<HealthyKnowledgeExamine> {

    void examineHealthyKnowledge(HealthyKnowledgeExamineDTO healthyKnowledgeExamineDTO);
}
