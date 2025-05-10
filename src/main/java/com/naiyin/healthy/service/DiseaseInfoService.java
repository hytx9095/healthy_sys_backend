package com.naiyin.healthy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.naiyin.healthy.model.dto.disease.DiseaseInfoDTO;
import com.naiyin.healthy.model.dto.disease.DiseaseInfoQueryDTO;
import com.naiyin.healthy.model.dto.disease.DiseaseInfoUpdateDTO;
import com.naiyin.healthy.model.entity.DiseaseInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.text.ParseException;

/**
* @author wang'ren
* @description 针对表【disease_info】的数据库操作Service
* @createDate 2025-04-15 14:44:12
*/
public interface DiseaseInfoService extends IService<DiseaseInfo> {

    DiseaseInfo getDiseaseInfo(Long DiseaseInfoId);

    void updateDiseaseInfo(DiseaseInfoUpdateDTO updateDTO) throws ParseException;

    void addDiseaseInfo(DiseaseInfoDTO diseaseInfoDTO) throws ParseException;

    void generateSuggestions();

    Page<DiseaseInfo> getDiseaseInfoPage(DiseaseInfoQueryDTO queryDTO);

    QueryWrapper<DiseaseInfo> getQueryWrapper(DiseaseInfoQueryDTO queryDTO);
}
