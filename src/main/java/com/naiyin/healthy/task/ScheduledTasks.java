package com.naiyin.healthy.task;

import cn.hutool.json.JSONUtil;
import com.naiyin.healthy.common.UserContext;
import com.naiyin.healthy.mapper.DietInfoMapper;
import com.naiyin.healthy.mapper.DiseaseInfoMapper;
import com.naiyin.healthy.mapper.SportInfoMapper;
import com.naiyin.healthy.model.entity.*;
import com.naiyin.healthy.rabbitmq.MQMessageProducer;
import com.naiyin.healthy.service.BasicHealthInfoService;
import com.naiyin.healthy.service.UserService;
import com.naiyin.healthy.service.UserTagsService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {

    private final UserService userService;
    private final UserTagsService userTagsService;
    private final MQMessageProducer mqMessageProducer;
    private final BasicHealthInfoService basicHealthInfoService;
    private final DietInfoMapper dietInfoMapper;
    private final DiseaseInfoMapper diseaseInfoMapper;
    private final SportInfoMapper sportInfoMapper;

    // 每天的特定时间执行一次
    @Scheduled(cron = "0 0 0 * * ?")
    public void doTaskEveryDayAtNoon() {
        List<User> list = userService.lambdaQuery().list();
        list.forEach(user -> sendMessage(user.getId()));
    }

    public void sendMessage(Long userId) {
        // 获取用户基础信息
        BasicHealthInfo basicHealthInfo = basicHealthInfoService.lambdaQuery().eq(BasicHealthInfo::getUserId, userId).one();
        // 获取用户饮食信息
        List<DietInfo> dietInfos = dietInfoMapper.selectRecentDietsByUserId(userId);
        // 获取用户疾病信息
        List<DiseaseInfo> diseaseInfos = diseaseInfoMapper.selectRecentDiseaseInfoByUserId(userId);
        // 获取用户运动信息
        List<SportInfo> sportInfos = sportInfoMapper.selectRecentSportInfoByUserId(userId);
        UserAllHealthyInfo userAllHealthyInfo = new UserAllHealthyInfo();
        userAllHealthyInfo.setUserId(userId);
        userAllHealthyInfo.setBasicHealthInfo(basicHealthInfo);
        userAllHealthyInfo.setDietInfos(dietInfos);
        userAllHealthyInfo.setDiseaseInfos(diseaseInfos);
        userAllHealthyInfo.setSportInfos(sportInfos);
        // 发送到消息队列
        mqMessageProducer.getHealthyTagsMessage(JSONUtil.toJsonStr(userAllHealthyInfo));
    }
}

