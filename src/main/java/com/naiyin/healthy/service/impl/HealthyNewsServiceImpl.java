package com.naiyin.healthy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.naiyin.healthy.common.UserContext;
import com.naiyin.healthy.common.UserInfo;
import com.naiyin.healthy.constant.CommonConstant;
import com.naiyin.healthy.enums.SysErrorEnum;
import com.naiyin.healthy.exception.CommonException;
import com.naiyin.healthy.model.dto.healthyNews.HealthyNewsDTO;
import com.naiyin.healthy.model.dto.healthyNews.HealthyNewsQueryDTO;
import com.naiyin.healthy.model.dto.healthyNews.HealthyNewsUpdateDTO;
import com.naiyin.healthy.model.entity.HealthyNews;
import com.naiyin.healthy.model.entity.User;
import com.naiyin.healthy.model.vo.HealthyNewsVO;
import com.naiyin.healthy.service.HealthyNewsService;
import com.naiyin.healthy.mapper.HealthyNewsMapper;
import com.naiyin.healthy.service.UserService;
import com.naiyin.healthy.util.SqlUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author wang'ren
* @description 针对表【healthy_news】的数据库操作Service实现
* @createDate 2025-04-19 22:07:05
*/
@Service
public class HealthyNewsServiceImpl extends ServiceImpl<HealthyNewsMapper, HealthyNews>
    implements HealthyNewsService{

    @Resource
    private UserService userService;
    @Override
    public HealthyNews getHealthyNews(Long healthyNewsId) {
        if (healthyNewsId == null){
            throw new CommonException(SysErrorEnum.PARAM_ERROR, "参数错误");
        }
        HealthyNews healthyNews = lambdaQuery()
                .eq(HealthyNews::getId, healthyNewsId)
                .one();
        if (healthyNews == null){
            throw new CommonException(SysErrorEnum.NOT_FOUND_ERROR, "用户饮食信息不存在");
        }
        return healthyNews;
    }

    @Override
    public void updateHealthyNews(HealthyNewsUpdateDTO updateDTO) {
        HealthyNews healthyNews = BeanUtil.copyProperties(updateDTO, HealthyNews.class);
        healthyNews.setId(updateDTO.getId());
        boolean result = updateById(healthyNews);
        if (!result){
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "更新失败");
        }
    }

    @Override
    public void addHealthyNews(HealthyNewsDTO healthyNewsDTO) throws ParseException {
        HealthyNews healthyNews = BeanUtil.copyProperties(healthyNewsDTO, HealthyNews.class);
        healthyNews.setUserId(UserContext.getUserId());
        boolean result = save(healthyNews);
        if (!result){
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "添加失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Page<HealthyNewsVO> getHealthyNewsVOPage(HealthyNewsQueryDTO healthyNewsDTO) {
        long current = healthyNewsDTO.getCurrent();
        long size = healthyNewsDTO.getPageSize();
        Page<HealthyNews> page = page(new Page<>(current, size)
                , getQueryWrapper(healthyNewsDTO));
        Page<HealthyNewsVO> healthyNewsVOPage = new Page<>(current, size, page.getTotal());
        List<HealthyNews> records = page.getRecords();
        List<HealthyNewsVO> healthyNewsVOList;
        if (CollectionUtils.isEmpty(records)) {
            healthyNewsVOList =  new ArrayList<>();
        }else {
            healthyNewsVOList = records.stream().map(this::getHealthyNewsVO).collect(Collectors.toList());
        }
        healthyNewsVOPage.setRecords(healthyNewsVOList);
        return healthyNewsVOPage;
    }

    @Override
    public QueryWrapper<HealthyNews> getQueryWrapper(HealthyNewsQueryDTO healthyNewsDTO) {
        if (ObjectUtil.isNull(healthyNewsDTO)){
            throw new CommonException(SysErrorEnum.PARAM_ERROR, "请求参数为空");
        }
        Integer isHome = healthyNewsDTO.getIsHome();
        String content = healthyNewsDTO.getContent();
        String sortField = healthyNewsDTO.getSortField();
        String sortOrder = healthyNewsDTO.getSortOrder();
        QueryWrapper<HealthyNews> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(ObjectUtil.isNotEmpty(content), "content", content);
        queryWrapper.eq(ObjectUtil.isNotNull(isHome), "is_home", isHome);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        return queryWrapper;
    }

    public HealthyNewsVO getHealthyNewsVO(HealthyNews healthyNews) {
        if (healthyNews == null) {
            return null;
        }
        HealthyNewsVO healthyNewsVO = BeanUtil.copyProperties(healthyNews, HealthyNewsVO.class);
        User user = userService.lambdaQuery().eq(User::getId, healthyNews.getUserId()).one();
        healthyNewsVO.setUsername(user.getUsername());
        healthyNewsVO.setUserAvatar(user.getUserAvatar());
        return healthyNewsVO;
    }
}




