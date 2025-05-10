package com.naiyin.healthy;

import cn.hutool.core.util.ObjectUtil;
import com.naiyin.healthy.mapper.DietInfoMapper;
import com.naiyin.healthy.mapper.DiseaseInfoMapper;
import com.naiyin.healthy.mapper.SportInfoMapper;
import com.naiyin.healthy.model.entity.*;
import com.naiyin.healthy.service.BasicHealthInfoService;
import com.naiyin.healthy.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
//@RequiredArgsConstructor
class SpringbootInitApplicationTests {

//    private final UserService userService;
//    private final BasicHealthInfoService basicHealthInfoService;
//    private final DietInfoMapper dietInfoMapper;
//    private final DiseaseInfoMapper diseaseInfoMapper;
//    private final SportInfoMapper sportInfoMapper;

    @Resource
    private UserService userService;
    @Resource
    private BasicHealthInfoService basicHealthInfoService;
    @Resource
    private DietInfoMapper dietInfoMapper;
    @Resource
    private DiseaseInfoMapper diseaseInfoMapper;
    @Resource
    private SportInfoMapper sportInfoMapper;

    @Test
    void contextLoads() {
        // 获取用户基础信息
        BasicHealthInfo basicHealthInfo = basicHealthInfoService.lambdaQuery().eq(BasicHealthInfo::getUserId,1913857834239348737L).one();
        // 获取用户饮食信息
        List<DietInfo> dietInfos = dietInfoMapper.selectRecentDietsByUserId(1913857834239348737L);
        // 获取用户疾病信息
        List<DiseaseInfo> diseaseInfos = diseaseInfoMapper.selectRecentDiseaseInfoByUserId(1913857834239348737L);
        // 获取用户运动信息
        List<SportInfo> sportInfos = sportInfoMapper.selectRecentSportInfoByUserId(1913857834239348737L);
        UserAllHealthyInfo userAllHealthyInfo = new UserAllHealthyInfo();
        userAllHealthyInfo.setBasicHealthInfo(basicHealthInfo);
        userAllHealthyInfo.setDietInfos(dietInfos);
        userAllHealthyInfo.setDiseaseInfos(diseaseInfos);
        userAllHealthyInfo.setSportInfos(sportInfos);
        System.out.println(ObjectUtil.toString(userAllHealthyInfo));
    }


}
