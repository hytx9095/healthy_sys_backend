package com.naiyin.healthy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.naiyin.healthy.common.UserContext;
import com.naiyin.healthy.enums.HeartRateEnum;
import com.naiyin.healthy.enums.SysErrorEnum;
import com.naiyin.healthy.exception.CommonException;
import com.naiyin.healthy.model.dto.BasicHealthInfoDTO;
import com.naiyin.healthy.model.entity.BasicHealthInfo;
import com.naiyin.healthy.model.vo.UserVO;
import com.naiyin.healthy.service.BasicHealthInfoService;
import com.naiyin.healthy.mapper.BasicHealthInfoMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* @author wang'ren
* @description 针对表【basic_health_info】的数据库操作Service实现
* @createDate 2024-11-29 11:17:02
*/
@Service
public class BasicHealthInfoServiceImpl extends ServiceImpl<BasicHealthInfoMapper, BasicHealthInfo>
    implements BasicHealthInfoService{

    @Override
    public BasicHealthInfo getBasicHealthInfo() {
        BasicHealthInfo basicHealthInfo = lambdaQuery()
                .eq(BasicHealthInfo::getUserId, UserContext.getUserId())
                .one();
        if (basicHealthInfo == null){
            throw new CommonException(SysErrorEnum.NOT_FOUND_ERROR, "用户基本健康信息不存在");
        }
        return basicHealthInfo;
    }

    @Override
    public void updateBasicHealthInfo(BasicHealthInfoDTO basicHealthInfoDTO) {
        BasicHealthInfo basicHealthInfo = lambdaQuery().eq(BasicHealthInfo::getUserId, UserContext.getUserId()).one();
        BeanUtil.copyProperties(basicHealthInfoDTO, basicHealthInfo);
        boolean result = updateById(basicHealthInfo);
        if (!result){
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "更新失败");
        }
    }

    @Override
    public void addBasicHealthInfo(BasicHealthInfoDTO basicHealthInfoDTO) {
        BasicHealthInfo basicHealthInfo = BeanUtil.copyProperties(basicHealthInfoDTO, BasicHealthInfo.class);
        basicHealthInfo.setUserId(UserContext.getUserId());
        boolean result = save(basicHealthInfo);
        if (!result){
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "添加失败");
        }
    }

    @Override
    public List<String> getBasicHealthyKeys() {
        List<String> keys = new ArrayList<>();
        BasicHealthInfo basicHealthInfo = lambdaQuery().eq(BasicHealthInfo::getUserId, UserContext.getUserId()).one();
        // bmi
        double bmi = basicHealthInfo.getBMI();
        if (bmi > 24) {
            keys.add("bmi偏高");
        } else if (bmi < 18.5) {
            keys.add("bmi偏低");
        }
        // 血压
        String[] bpParts = basicHealthInfo.getBloodPressure().split("/");
        if (bpParts.length == 2) {
            int systolic = Integer.parseInt(bpParts[0]);
            int diastolic = Integer.parseInt(bpParts[1]);
            if (systolic > 140 || diastolic > 90) {
                keys.add("血压偏高");
            } else if (systolic < 90 || diastolic < 60) {
                keys.add("血压偏低");
            }
        }
        // 心率
        int heartRate = basicHealthInfo.getHeartRate();
        int age = basicHealthInfo.getAge();
        if (heartRate < HeartRateEnum.getRateMin(age)){
            keys.add("心率过低");
        } else if (heartRate > HeartRateEnum.getRateMax(age)){
            keys.add("心率过高");
        }
        // 体温
        Double bodyTemperature = basicHealthInfo.getBodyTemperature();
        if (bodyTemperature < 36.1){
            keys.add("体温偏低");
        } else if (bodyTemperature > 37.0){
            keys.add("体温偏高");
        }
        return keys;
    }


}




