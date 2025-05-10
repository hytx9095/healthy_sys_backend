package com.naiyin.healthy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.naiyin.healthy.model.dto.dietInfo.DietInfoQueryDTO;
import com.naiyin.healthy.model.dto.sportInfo.SportInfoDTO;
import com.naiyin.healthy.model.dto.sportInfo.SportInfoQueryDTO;
import com.naiyin.healthy.model.dto.sportInfo.SportInfoUpdateDTO;
import com.naiyin.healthy.model.entity.DietInfo;
import com.naiyin.healthy.model.entity.SportInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.text.ParseException;

/**
* @author wang'ren
* @description 针对表【sport_info】的数据库操作Service
* @createDate 2024-12-09 17:05:18
*/
public interface SportInfoService extends IService<SportInfo> {

    SportInfo getSportInfo(Long sportInfoId);

    void updateSportInfo(SportInfoUpdateDTO updateDTO) throws ParseException;

    void addSportInfo(SportInfoDTO sportInfoDTO) throws ParseException;

    void generateSuggestions();

    Page<SportInfo> getSportInfoPage(SportInfoQueryDTO queryDTO);

    QueryWrapper<SportInfo> getQueryWrapper(SportInfoQueryDTO queryDTO);
}
