package com.naiyin.healthy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.naiyin.healthy.model.dto.dietInfo.DietInfoDTO;
import com.naiyin.healthy.model.dto.dietInfo.DietInfoQueryDTO;
import com.naiyin.healthy.model.dto.dietInfo.DietInfoUpdateDTO;
import com.naiyin.healthy.model.entity.DietInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.naiyin.healthy.model.vo.DietInfoVO;

import java.text.ParseException;

/**
* @author wang'ren
* @description 针对表【diet_info】的数据库操作Service
* @createDate 2024-12-09 17:05:04
*/
public interface DietInfoService extends IService<DietInfo> {
    
    DietInfo getDietInfo(Long dietInfoId);

    void updateDietInfo(DietInfoUpdateDTO updateDTO) throws ParseException;

    void addDietInfo(DietInfoDTO dietInfoDTO) throws ParseException;

    Page<DietInfo> getDietInfoPage(DietInfoQueryDTO dietInfoDTO);

    QueryWrapper<DietInfo> getQueryWrapper(DietInfoQueryDTO dietInfoDTO);
}
