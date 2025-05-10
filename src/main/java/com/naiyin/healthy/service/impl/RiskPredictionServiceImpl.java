package com.naiyin.healthy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.naiyin.healthy.common.R;
import com.naiyin.healthy.common.UserContext;
import com.naiyin.healthy.constant.CommonConstant;
import com.naiyin.healthy.enums.SysErrorEnum;
import com.naiyin.healthy.exception.CommonException;
import com.naiyin.healthy.mapper.DietInfoMapper;
import com.naiyin.healthy.mapper.DiseaseInfoMapper;
import com.naiyin.healthy.mapper.SportInfoMapper;
import com.naiyin.healthy.model.dto.RiskPrediction.RiskPredictionDTO;
import com.naiyin.healthy.model.dto.RiskPrediction.RiskPredictionQueryDTO;
import com.naiyin.healthy.model.dto.dietInfo.DietInfoQueryDTO;
import com.naiyin.healthy.model.entity.*;
import com.naiyin.healthy.rabbitmq.MQMessageConsumer;
import com.naiyin.healthy.rabbitmq.MQMessageProducer;
import com.naiyin.healthy.service.BasicHealthInfoService;
import com.naiyin.healthy.service.RiskPredictionService;
import com.naiyin.healthy.mapper.RiskPredictionMapper;
import com.naiyin.healthy.util.SqlUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
* @author wang'ren
* @description 针对表【risk_prediction】的数据库操作Service实现
* @createDate 2024-12-09 19:21:20
*/
@Service
@RequiredArgsConstructor
public class RiskPredictionServiceImpl extends ServiceImpl<RiskPredictionMapper, RiskPrediction>
    implements RiskPredictionService{

    private final MQMessageProducer mqMessageProducer;
    private final BasicHealthInfoService basicHealthInfoService;
    private final DietInfoMapper dietInfoMapper;
    private final DiseaseInfoMapper diseaseInfoMapper;
    private final SportInfoMapper sportInfoMapper;

    @Override
    public RiskPrediction getRiskPrediction() {
        RiskPrediction riskPrediction = lambdaQuery()
                .eq(RiskPrediction::getUserId, UserContext.getUserId())
                .one();
        if (riskPrediction == null){
            throw new CommonException(SysErrorEnum.NOT_FOUND_ERROR, "用户基本健康信息不存在");
        }
        return riskPrediction;
    }

    @Override
    public void updateRiskPrediction(RiskPredictionDTO riskPredictionDTO) {
        RiskPrediction riskPrediction = new RiskPrediction();
        riskPrediction.setUserId(UserContext.getUserId());
        BeanUtil.copyProperties(riskPredictionDTO, riskPrediction);
        boolean result = updateById(riskPrediction);
        if (!result){
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "更新失败");
        }
    }

    @Override
    public void addRiskPrediction(RiskPredictionDTO riskPredictionDTO) {
        RiskPrediction riskPrediction = BeanUtil.copyProperties(riskPredictionDTO, RiskPrediction.class);
        riskPrediction.setUserId(UserContext.getUserId());
        boolean result = save(riskPrediction);
        if (!result){
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "添加失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void generateRiskPrediction() {
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
        // 发送到消息队列
        mqMessageProducer.getRiskPredictionMessage(JSONUtil.toJsonStr(userAllHealthyInfo));
        // 修改当前建议状态
        List<RiskPrediction> list = lambdaQuery().eq(RiskPrediction::getUserId, UserContext.getUserId()).eq(RiskPrediction::getIsRead, 0).list();

        if (list.size() >0){
            for (RiskPrediction riskPrediction : list) {
                riskPrediction.setIsRead(1);
            }
            boolean b = updateBatchById(list);
            if (!b){
                throw new CommonException(SysErrorEnum.OPERATION_ERROR, "修改失败");
            }
        }

    }

    @Override
    public Page<RiskPrediction> getRiskPredictionPage(RiskPredictionQueryDTO riskPredictionQueryDTO) {
        Page<RiskPrediction> page = page(new Page<>(riskPredictionQueryDTO.getCurrent()
                , riskPredictionQueryDTO.getPageSize()), getQueryWrapper(riskPredictionQueryDTO));
        return page;
    }

    public QueryWrapper<RiskPrediction> getQueryWrapper(RiskPredictionQueryDTO riskPredictionQueryDTO) {
        if (ObjectUtil.isNull(riskPredictionQueryDTO)){
            throw new CommonException(SysErrorEnum.PARAM_ERROR, "请求参数为空");
        }
        Integer isRead = riskPredictionQueryDTO.getIsRead();
        String sortField = riskPredictionQueryDTO.getSortField();
        String sortOrder = riskPredictionQueryDTO.getSortOrder();
        QueryWrapper<RiskPrediction> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotNull(isRead), "is_read", isRead);
        queryWrapper.eq("user_id", UserContext.getUserId());
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        return queryWrapper;
    }
}




