package com.naiyin.healthy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.naiyin.healthy.common.UserContext;
import com.naiyin.healthy.constant.CommonConstant;
import com.naiyin.healthy.enums.SysErrorEnum;
import com.naiyin.healthy.exception.CommonException;
import com.naiyin.healthy.model.dto.disease.DiseaseInfoDTO;
import com.naiyin.healthy.model.dto.disease.DiseaseInfoQueryDTO;
import com.naiyin.healthy.model.dto.disease.DiseaseInfoUpdateDTO;
import com.naiyin.healthy.model.entity.DiseaseInfo;
import com.naiyin.healthy.service.DiseaseInfoService;
import com.naiyin.healthy.mapper.DiseaseInfoMapper;
import com.naiyin.healthy.util.SqlUtils;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
* @author wang'ren
* @description 针对表【disease_info】的数据库操作Service实现
* @createDate 2025-04-15 14:44:12
*/
@Service
public class DiseaseInfoServiceImpl extends ServiceImpl<DiseaseInfoMapper, DiseaseInfo>
    implements DiseaseInfoService{

    @Override
    public DiseaseInfo getDiseaseInfo(Long diseaseInfoId) {
        DiseaseInfo diseaseInfo = lambdaQuery()
                .eq(DiseaseInfo::getId, diseaseInfoId)
                .one();
        if (diseaseInfo == null){
            throw new CommonException(SysErrorEnum.NOT_FOUND_ERROR, "用户运动信息不存在");
        }
        return diseaseInfo;
    }

    @Override
    public void updateDiseaseInfo(DiseaseInfoUpdateDTO updateDTO) throws ParseException {
        DiseaseInfo diseaseInfo = BeanUtil.copyProperties(updateDTO, DiseaseInfo.class);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse(updateDTO.getHappenTime());
        diseaseInfo.setUserId(UserContext.getUserId());
        diseaseInfo.setId(updateDTO.getDiseaseInfoId());
        diseaseInfo.setHappenTime(date);
        boolean result = updateById(diseaseInfo);
        if (!result){
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "更新失败");
        }
    }

    @Override
    public void addDiseaseInfo(DiseaseInfoDTO diseaseInfoDTO) throws ParseException {
        DiseaseInfo DiseaseInfo = BeanUtil.copyProperties(diseaseInfoDTO, DiseaseInfo.class);
        DiseaseInfo.setUserId(UserContext.getUserId());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse(diseaseInfoDTO.getHappenTime());
        DiseaseInfo.setHappenTime(date);
        boolean result = save(DiseaseInfo);
        if (!result){
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "添加失败");
        }
    }

    @Override
    public Page<DiseaseInfo> getDiseaseInfoPage(DiseaseInfoQueryDTO queryDTO) {
        Page<DiseaseInfo> page = page(new Page<>(queryDTO.getCurrent(), queryDTO.getPageSize())
                , getQueryWrapper(queryDTO));
        return page;
    }

    @Override
    public QueryWrapper<DiseaseInfo> getQueryWrapper(DiseaseInfoQueryDTO queryDTO) {
        if (ObjectUtil.isNull(queryDTO)){
            throw new CommonException(SysErrorEnum.PARAM_ERROR, "请求参数为空");
        }
        Date happenTime = queryDTO.getHappenTime();
        String date = "";
        if (ObjectUtil.isNotEmpty(happenTime)){
            date = new SimpleDateFormat("yyyy-MM-dd").format(happenTime);
        }
        String diseaseName = queryDTO.getDiseaseName();
        String sortField = queryDTO.getSortField();
        String sortOrder = queryDTO.getSortOrder();
        QueryWrapper<DiseaseInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", UserContext.getUserId());
        queryWrapper.like(StrUtil.isNotBlank(diseaseName), "disease_name", diseaseName);
        queryWrapper.like(StrUtil.isNotBlank(date), "occurrence_time", date);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        return queryWrapper;
    }
    @Override
    public void generateSuggestions() {
        // TODO:
    }
}




