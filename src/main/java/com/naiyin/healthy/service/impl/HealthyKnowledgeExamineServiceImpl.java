package com.naiyin.healthy.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.naiyin.healthy.enums.HealthyKnowledgeExamineEnum;
import com.naiyin.healthy.enums.HealthyKnowledgeStatusEnum;
import com.naiyin.healthy.enums.SysErrorEnum;
import com.naiyin.healthy.exception.CommonException;
import com.naiyin.healthy.model.dto.healthyKnowledge.HealthyKnowledgeExamineDTO;
import com.naiyin.healthy.model.entity.HealthyKnowledge;
import com.naiyin.healthy.model.entity.HealthyKnowledgeExamine;
import com.naiyin.healthy.service.HealthyKnowledgeExamineService;
import com.naiyin.healthy.mapper.HealthyKnowledgeExamineMapper;
import com.naiyin.healthy.service.HealthyKnowledgeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author wang'ren
* @description 针对表【healthy_knowledge_examine】的数据库操作Service实现
* @createDate 2025-04-13 13:21:13
*/
@Service
@AllArgsConstructor
public class HealthyKnowledgeExamineServiceImpl extends ServiceImpl<HealthyKnowledgeExamineMapper, HealthyKnowledgeExamine>
    implements HealthyKnowledgeExamineService{

    private final HealthyKnowledgeService healthyKnowledgeService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void examineHealthyKnowledge(HealthyKnowledgeExamineDTO healthyKnowledgeExamineDTO) {
        HealthyKnowledgeExamine healthyKnowledgeExamine = new HealthyKnowledgeExamine();
        Long healthyKnowledgeId = healthyKnowledgeExamineDTO.getHealthyKnowledgeId();
        String result = healthyKnowledgeExamineDTO.getResult();
        String description = healthyKnowledgeExamineDTO.getDescription();
        if (result.equals(HealthyKnowledgeExamineEnum.NOT_PASS.getText())){
            if (StrUtil.isBlank(description)){
                throw new CommonException(SysErrorEnum.PARAM_ERROR, "请填写驳回原因");
            }
            healthyKnowledgeExamine.setHealthyKnowledgeId(healthyKnowledgeId);
            healthyKnowledgeExamine.setResult(HealthyKnowledgeExamineEnum.NOT_PASS.getValue());
            healthyKnowledgeExamine.setDescription(description);
            boolean save = save(healthyKnowledgeExamine);
            if (!save){
                throw new CommonException(SysErrorEnum.OPERATION_ERROR, "审核失败");
            }
            HealthyKnowledge healthyKnowledge = new HealthyKnowledge();
            healthyKnowledge.setId(healthyKnowledgeId);
            healthyKnowledge.setStatus(HealthyKnowledgeStatusEnum.EXAMINE_FAILED.getValue());
            boolean update = healthyKnowledgeService.updateById(healthyKnowledge);
            if (!update){
                throw new CommonException(SysErrorEnum.OPERATION_ERROR, "更新失败");
            }
        }
        if (result.equals(HealthyKnowledgeExamineEnum.PASS.getText())){
            healthyKnowledgeExamine.setHealthyKnowledgeId(healthyKnowledgeId);
            healthyKnowledgeExamine.setResult(HealthyKnowledgeExamineEnum.PASS.getValue());
            healthyKnowledgeExamine.setDescription(description);
            boolean save = save(healthyKnowledgeExamine);
            if (!save){
                throw new CommonException(SysErrorEnum.OPERATION_ERROR, "审核失败");
            }
            HealthyKnowledge healthyKnowledge = new HealthyKnowledge();
            healthyKnowledge.setId(healthyKnowledgeId);
            healthyKnowledge.setStatus(HealthyKnowledgeStatusEnum.PASS.getValue());
            boolean update = healthyKnowledgeService.updateById(healthyKnowledge);
            if (!update){
                throw new CommonException(SysErrorEnum.OPERATION_ERROR, "更新失败");
            }
        }

    }
}




