package com.naiyin.healthy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.naiyin.healthy.common.UserContext;
import com.naiyin.healthy.constant.CommonConstant;
import com.naiyin.healthy.enums.HealthyKnowledgeStatusEnum;
import com.naiyin.healthy.enums.SysErrorEnum;
import com.naiyin.healthy.exception.CommonException;
import com.naiyin.healthy.model.dto.healthyKnowledge.HealthyKnowledgeStarDTO;
import com.naiyin.healthy.model.dto.suggestions.SuggestionDTO;
import com.naiyin.healthy.model.dto.healthyKnowledge.HealthyKnowledgeDTO;
import com.naiyin.healthy.model.dto.healthyKnowledge.HealthyKnowledgeListDTO;
import com.naiyin.healthy.model.dto.healthyKnowledge.HealthyKnowledgeQueryDTO;
import com.naiyin.healthy.model.entity.HealthyKnowledge;
import com.naiyin.healthy.model.entity.HealthyKnowledgeStar;
import com.naiyin.healthy.model.entity.User;
import com.naiyin.healthy.model.entity.UserTags;
import com.naiyin.healthy.model.vo.HealthyKnowledgeVO;
import com.naiyin.healthy.service.HealthyKnowledgeService;
import com.naiyin.healthy.mapper.HealthyKnowledgeMapper;
import com.naiyin.healthy.service.HealthyKnowledgeStarService;
import com.naiyin.healthy.service.UserService;
import com.naiyin.healthy.service.UserTagsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author wang'ren
* @description 针对表【healthy_knowledge】的数据库操作Service实现
* @createDate 2024-12-09 17:05:30
*/
@Service
public class HealthyKnowledgeServiceImpl extends ServiceImpl<HealthyKnowledgeMapper, HealthyKnowledge>
    implements HealthyKnowledgeService{

    @Resource
    private UserService userService;

    @Resource
    private HealthyKnowledgeStarService healthyKnowledgeStarService;

    @Resource
    private HealthyKnowledgeMapper healthyKnowledgeMapper;

    @Resource
    private UserTagsService userTagsService;

    @Override
    public HealthyKnowledge getHealthyKnowledge() {
        HealthyKnowledge healthyKnowledge = lambdaQuery()
                .eq(HealthyKnowledge::getUserId, UserContext.getUserId())
                .one();
        if (healthyKnowledge == null){
            throw new CommonException(SysErrorEnum.NOT_FOUND_ERROR, "用户基本健康信息不存在");
        }
        return healthyKnowledge;
    }

    @Override
    public void updateHealthyKnowledge(HealthyKnowledgeDTO healthyKnowledgeDTO) {
        HealthyKnowledge healthyKnowledge = new HealthyKnowledge();
        BeanUtil.copyProperties(healthyKnowledgeDTO, healthyKnowledge);
        healthyKnowledge.setId(healthyKnowledgeDTO.getHealthyKnowledgeId());
        boolean result = updateById(healthyKnowledge);
        if (!result){
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "更新失败");
        }
    }

    @Override
    public void addHealthyKnowledge(HealthyKnowledgeDTO healthyKnowledgeDTO) {
        HealthyKnowledge healthyKnowledge = new HealthyKnowledge();
        healthyKnowledge.setType(healthyKnowledgeDTO.getType());
        healthyKnowledge.setContent(healthyKnowledgeDTO.getContent());
        healthyKnowledge.setTags(JSONUtil.toJsonStr(healthyKnowledgeDTO.getTags()));
        healthyKnowledge.setUserId(UserContext.getUserId());
        healthyKnowledge.setStatus(HealthyKnowledgeStatusEnum.PASS.getValue());
        boolean result = save(healthyKnowledge);
        if (!result){
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "添加失败");
        }
    }


    @Override
    public void addHealthyKnowledgeList(HealthyKnowledgeListDTO healthyKnowledgeListDTO) {
        if (ObjectUtil.isNull(healthyKnowledgeListDTO)){
            throw new CommonException(SysErrorEnum.PARAM_ERROR, "请求参数为空");
        }
        if (healthyKnowledgeListDTO.getHealthyKnowledgeDTOS().size() == 0){
            throw new CommonException(SysErrorEnum.PARAM_ERROR, "请求参数为空");
        }
        for (HealthyKnowledgeDTO healthyKnowledgeDTO : healthyKnowledgeListDTO.getHealthyKnowledgeDTOS()) {
            addHealthyKnowledge(healthyKnowledgeDTO);
        }
    }

    @Override
    public List<HealthyKnowledge> getKnowledgeListByType(SuggestionDTO suggestionDTO) {
        return null;
    }

    @Override
    public void shareHealthyKnowledge(HealthyKnowledgeDTO healthyKnowledgeDTO) {
        HealthyKnowledge healthyKnowledge = new HealthyKnowledge();
        healthyKnowledge.setType(healthyKnowledgeDTO.getType());
        healthyKnowledge.setContent(healthyKnowledgeDTO.getContent());
        healthyKnowledge.setTags(JSONUtil.toJsonStr(healthyKnowledgeDTO.getTags()));
        healthyKnowledge.setUserId(UserContext.getUserId());
        healthyKnowledge.setStatus(HealthyKnowledgeStatusEnum.PENDING_EXAMINE.getValue());
        boolean result = save(healthyKnowledge);
        if (!result){
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "添加失败");
        }
    }

    @Override
    public void starHealthyKnowledge(HealthyKnowledgeStarDTO healthyKnowledgeStarDTO) {
        HealthyKnowledgeStar star = healthyKnowledgeStarService.lambdaQuery().eq(HealthyKnowledgeStar::getHealthyKnowledgeId, healthyKnowledgeStarDTO.getHealthyKnowledgeId())
                .eq(HealthyKnowledgeStar::getUserId, UserContext.getUserId())
                .eq(HealthyKnowledgeStar::getIsDelete, 1).one();
        if (star != null){
            star.setIsDelete(0);
            boolean b = healthyKnowledgeStarService.updateById(star);
            if (!b){
                throw new CommonException(SysErrorEnum.OPERATION_ERROR, "收藏失败");
            }
        } else {
            HealthyKnowledgeStar healthyKnowledgeStar = new HealthyKnowledgeStar();
            healthyKnowledgeStar.setUserId(UserContext.getUserId());
            healthyKnowledgeStar.setHealthyKnowledgeId(healthyKnowledgeStarDTO.getHealthyKnowledgeId());
            boolean save = healthyKnowledgeStarService.save(healthyKnowledgeStar);
            if (!save) {
                throw new CommonException(SysErrorEnum.OPERATION_ERROR, "收藏失败");
            }
        }
    }

    @Override
    public void cancelHealthyKnowledge(HealthyKnowledgeStarDTO healthyKnowledgeStarDTO) {
        HealthyKnowledgeStar healthyKnowledgeStar = healthyKnowledgeStarService.lambdaQuery().eq(HealthyKnowledgeStar::getHealthyKnowledgeId, healthyKnowledgeStarDTO.getHealthyKnowledgeId())
                .eq(HealthyKnowledgeStar::getUserId, UserContext.getUserId()).one();
        if (ObjectUtil.isNull(healthyKnowledgeStar)){
            throw new CommonException(SysErrorEnum.NOT_FOUND_ERROR, "用户收藏信息不存在");
        }
        boolean b = healthyKnowledgeStarService.removeById(healthyKnowledgeStar.getId());
        if (!b){
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "取消收藏失败");
        }
    }

    @Override
    public Page<HealthyKnowledgeVO> getHealthyKnowledgePage(HealthyKnowledgeQueryDTO healthyKnowledgeQueryDTO) {
        long current = healthyKnowledgeQueryDTO.getCurrent();
        long size = healthyKnowledgeQueryDTO.getPageSize();
        Page<HealthyKnowledge> page = page(new Page<>(current, size)
                , getQueryWrapper(healthyKnowledgeQueryDTO));
        Page<HealthyKnowledgeVO> healthyKnowledgeVOPage = new Page<>(current, size, page.getTotal());
        List<HealthyKnowledge> records = page.getRecords();
        List<HealthyKnowledgeVO> healthyKnowledgeVOList;
        if (CollectionUtils.isEmpty(records)) {
            healthyKnowledgeVOList =  new ArrayList<>();
        }else {
            healthyKnowledgeVOList = records.stream().map(this::getHealthyKnowledgeVO).collect(Collectors.toList());
        }
        healthyKnowledgeVOPage.setRecords(healthyKnowledgeVOList);
        return healthyKnowledgeVOPage;
    }

    @Override
    public List<HealthyKnowledgeVO> getHomeHealthyKnowledgePage(HealthyKnowledgeQueryDTO healthyKnowledgeQueryDTO) {
        UserTags userTags = userTagsService.lambdaQuery()
                .eq(UserTags::getUserId, UserContext.getUserId())
                .between(UserTags::getCreateTime,
                        LocalDate.now().atStartOfDay(),
                        LocalDate.now().atTime(23, 59, 59))
                .one();
        if (ObjectUtil.isNull(userTags)){
            throw new CommonException(SysErrorEnum.NOT_FOUND_ERROR, "信息不存在");
        }
        List<String> list = JSONUtil.toList(userTags.getTags(), String.class);
        List<HealthyKnowledgeVO> healthyKnowledgeVOList = healthyKnowledgeMapper.selectByTagList(list);
        if (healthyKnowledgeVOList.size() < 9){
            List<HealthyKnowledgeVO> newHealthyKnowledgeVOList = healthyKnowledgeMapper.selectNewHealthyKnowledgeList(9 - healthyKnowledgeVOList.size());
            for (HealthyKnowledgeVO healthyKnowledgeVO : newHealthyKnowledgeVOList) {
                healthyKnowledgeVOList.add(healthyKnowledgeVO);
            }
            return healthyKnowledgeVOList;
        }
        return healthyKnowledgeVOList;
    }

    @Override
    public IPage<HealthyKnowledgeVO> getStarHealthyKnowledgePage(HealthyKnowledgeQueryDTO healthyKnowledgeQueryDTO) {
        long pageSize = healthyKnowledgeQueryDTO.getPageSize();
        long current = healthyKnowledgeQueryDTO.getCurrent();
        Page<HealthyKnowledgeVO> page = new Page<>(current, pageSize);
        if (StrUtil.isNotBlank(healthyKnowledgeQueryDTO.getContent())){
            IPage<HealthyKnowledgeVO> healthyKnowledgePage = healthyKnowledgeMapper
                    .getHealthyKnowledgePageByContent(page,
                            UserContext.getUserId(),
                            healthyKnowledgeQueryDTO.getStatus(),
                            healthyKnowledgeQueryDTO.getContent());
            return healthyKnowledgePage;
        } else {
            IPage<HealthyKnowledgeVO> healthyKnowledgePage = healthyKnowledgeMapper
                    .getHealthyKnowledgePage(page, UserContext.getUserId(), healthyKnowledgeQueryDTO.getStatus());
            return healthyKnowledgePage;
        }
    }

    @Override
    public IPage<HealthyKnowledgeVO> getStarHealthyKnowledgePageById(HealthyKnowledgeQueryDTO healthyKnowledgeQueryDTO) {
        long pageSize = healthyKnowledgeQueryDTO.getPageSize();
        long current = healthyKnowledgeQueryDTO.getCurrent();
        Page<HealthyKnowledgeVO> page = new Page<>(current, pageSize);
        IPage<HealthyKnowledgeVO> healthyKnowledgePage = healthyKnowledgeMapper
                .getHealthyKnowledgePageById(page, UserContext.getUserId());
        return healthyKnowledgePage;
    }

    @Override
    public IPage<HealthyKnowledgeVO> getStarredHealthyKnowledgePage(HealthyKnowledgeQueryDTO healthyKnowledgeQueryDTO) {
        long pageSize = healthyKnowledgeQueryDTO.getPageSize();
        long current = healthyKnowledgeQueryDTO.getCurrent();
        Page<HealthyKnowledgeVO> page = new Page<>(current, pageSize);
        IPage<HealthyKnowledgeVO> healthyKnowledgePage = healthyKnowledgeMapper
                .getStarredHealthyKnowledgePage(page, UserContext.getUserId(), healthyKnowledgeQueryDTO.getStatus());
        return healthyKnowledgePage;
    }

    @Override
    public QueryWrapper<HealthyKnowledge> getQueryWrapper(HealthyKnowledgeQueryDTO healthyKnowledgeQueryDTO) {
        if (healthyKnowledgeQueryDTO == null) {
            throw new CommonException(SysErrorEnum.PARAM_ERROR, "请求参数为空");
        }

        Long userId = healthyKnowledgeQueryDTO.getUserId();
        Integer type = healthyKnowledgeQueryDTO.getType();
        Integer status = healthyKnowledgeQueryDTO.getStatus();
        String content = healthyKnowledgeQueryDTO.getContent();
        String searchCondition = healthyKnowledgeQueryDTO.getSearchCondition();
        String sortField = healthyKnowledgeQueryDTO.getSortField();
        String sortOrder = healthyKnowledgeQueryDTO.getSortOrder();
        QueryWrapper<HealthyKnowledge> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(userId != null, "user_id", userId);
        queryWrapper.eq(type != null, "type", type);
        queryWrapper.eq(status != null, "status", status);
        if (StrUtil.isNotEmpty(searchCondition) && searchCondition.equals("exam")){
            queryWrapper.in("status", HealthyKnowledgeStatusEnum.PASS.getValue(), HealthyKnowledgeStatusEnum.EXAMINE_FAILED.getValue());
        } else if (StrUtil.isNotEmpty(searchCondition) && searchCondition.equals("notExam")) {
            queryWrapper.eq("status", HealthyKnowledgeStatusEnum.PENDING_EXAMINE.getValue());
        }
        queryWrapper.like(StrUtil.isNotBlank(content), "content", content);
        queryWrapper.orderBy(StrUtil.isNotBlank(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    public HealthyKnowledgeVO getHealthyKnowledgeVO(HealthyKnowledge healthyKnowledge) {
        if (healthyKnowledge == null) {
            return null;
        }
        HealthyKnowledgeVO healthyKnowledgeVO = BeanUtil.copyProperties(healthyKnowledge, HealthyKnowledgeVO.class);
        User user = userService.lambdaQuery().eq(User::getId, healthyKnowledge.getUserId()).one();
        healthyKnowledgeVO.setUsername(user.getUsername());
        healthyKnowledgeVO.setUserAvatar(user.getUserAvatar());
        return healthyKnowledgeVO;
    }
}




