package com.naiyin.healthy.service;

import com.naiyin.healthy.model.dto.BasicHealthInfoDTO;
import com.naiyin.healthy.model.entity.BasicHealthInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author wang'ren
* @description 针对表【basic_health_info】的数据库操作Service
* @createDate 2024-11-29 11:17:02
*/
public interface BasicHealthInfoService extends IService<BasicHealthInfo> {

    BasicHealthInfo getBasicHealthInfo();

    void updateBasicHealthInfo(BasicHealthInfoDTO basicHealthInfoDTO);

    void addBasicHealthInfo(BasicHealthInfoDTO basicHealthInfoDTO);
}

