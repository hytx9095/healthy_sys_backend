package com.naiyin.healthy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.naiyin.healthy.model.dto.healthyNews.HealthyNewsDTO;
import com.naiyin.healthy.model.dto.healthyNews.HealthyNewsQueryDTO;
import com.naiyin.healthy.model.dto.healthyNews.HealthyNewsUpdateDTO;
import com.naiyin.healthy.model.entity.HealthyNews;
import com.baomidou.mybatisplus.extension.service.IService;
import com.naiyin.healthy.model.vo.HealthyNewsVO;

import java.text.ParseException;

/**
* @author wang'ren
* @description 针对表【healthy_news】的数据库操作Service
* @createDate 2025-04-19 22:07:05
*/
public interface HealthyNewsService extends IService<HealthyNews> {
    HealthyNews getHealthyNews(Long healthyNewsId);

    void updateHealthyNews(HealthyNewsUpdateDTO updateDTO);

    void addHealthyNews(HealthyNewsDTO healthyNewsDTO) throws ParseException;

    Page<HealthyNewsVO> getHealthyNewsVOPage(HealthyNewsQueryDTO healthyNewsDTO);

    QueryWrapper<HealthyNews> getQueryWrapper(HealthyNewsQueryDTO healthyNewsDTO);
}
