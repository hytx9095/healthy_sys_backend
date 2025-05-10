package com.naiyin.healthy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.naiyin.healthy.common.UserContext;
import com.naiyin.healthy.enums.SysErrorEnum;
import com.naiyin.healthy.exception.CommonException;
import com.naiyin.healthy.model.dto.BasicHealthInfoDTO;
import com.naiyin.healthy.model.entity.BasicHealthInfo;
import com.naiyin.healthy.model.vo.UserVO;
import com.naiyin.healthy.service.BasicHealthInfoService;
import com.naiyin.healthy.mapper.BasicHealthInfoMapper;
import org.springframework.stereotype.Service;

import java.util.Date;

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
}




