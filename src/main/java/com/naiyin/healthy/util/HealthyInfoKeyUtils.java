package com.naiyin.healthy.util;

import com.naiyin.healthy.common.UserContext;
import com.naiyin.healthy.mapper.DietInfoMapper;
import com.naiyin.healthy.mapper.DiseaseInfoMapper;
import com.naiyin.healthy.mapper.SportInfoMapper;
import com.naiyin.healthy.model.entity.*;
import com.naiyin.healthy.service.BasicHealthInfoService;
import com.naiyin.healthy.service.DietInfoService;
import com.naiyin.healthy.service.DiseaseInfoService;
import com.naiyin.healthy.service.SportInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class HealthyInfoKeyUtils {

    private final BasicHealthInfoService basicHealthInfoService;
    private final DietInfoMapper dietInfoMapper;
    private final DiseaseInfoMapper diseaseInfoMapper;
    private final SportInfoMapper sportInfoMapper;
    private final DietInfoService dietInfoService;
    private final DiseaseInfoService diseaseInfoService;
    private final SportInfoService sportInfoService;

    public UserAllHealthyInfo getHealthyInfoKeys() {
        // 获取用户基础信息
        BasicHealthInfo basicHealthInfo = basicHealthInfoService.lambdaQuery().eq(BasicHealthInfo::getUserId, UserContext.getUserId()).one();
        // 获取用户饮食信息
        List<DietInfo> dietInfos = dietInfoMapper.selectRecentDietsByUserId(UserContext.getUserId());
        // 获取用户疾病信息
        List<DiseaseInfo> diseaseInfos = diseaseInfoMapper.selectRecentDiseaseInfoByUserId(UserContext.getUserId());
        // 获取用户运动信息
        List<SportInfo> sportInfos = sportInfoMapper.selectRecentSportInfoByUserId(UserContext.getUserId());
        UserAllHealthyInfo userAllHealthyInfo = new UserAllHealthyInfo();
        userAllHealthyInfo.setUserId(UserContext.getUserId());
        userAllHealthyInfo.setBasicHealthInfo(basicHealthInfo);
        userAllHealthyInfo.setDietInfos(dietInfos);
        userAllHealthyInfo.setDiseaseInfos(diseaseInfos);
        userAllHealthyInfo.setSportInfos(sportInfos);
        return userAllHealthyInfo;
    }

    public List<String> getHealthyInfoKeyList() {
        List<String> keys = new ArrayList<>();
        // 获取用户基础信息
        List<String> basicHealthyKeys = basicHealthInfoService.getBasicHealthyKeys();
        // 获取用户饮食信息
        List<DietInfo> dietInfos = dietInfoMapper.selectRecentDietsByUserId(UserContext.getUserId());
        // 获取用户疾病信息
        List<DiseaseInfo> diseaseInfos = diseaseInfoMapper.selectRecentDiseaseInfoByUserId(UserContext.getUserId());
        // 获取用户运动信息
        List<SportInfo> sportInfos = sportInfoMapper.selectRecentSportInfoByUserId(UserContext.getUserId());
        return keys;
    }
}
