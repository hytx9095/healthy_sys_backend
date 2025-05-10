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
import com.naiyin.healthy.model.dto.dietInfo.DietInfoDTO;
import com.naiyin.healthy.model.dto.dietInfo.DietInfoQueryDTO;
import com.naiyin.healthy.model.dto.dietInfo.DietInfoUpdateDTO;
import com.naiyin.healthy.model.entity.DietInfo;
import com.naiyin.healthy.service.DietInfoService;
import com.naiyin.healthy.mapper.DietInfoMapper;
import com.naiyin.healthy.util.SqlUtils;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
* @author wang'ren
* @description 针对表【diet_info】的数据库操作Service实现
* @createDate 2024-12-09 17:05:04
*/
@Service
public class DietInfoServiceImpl extends ServiceImpl<DietInfoMapper, DietInfo>
    implements DietInfoService{

    @Override
    public DietInfo getDietInfo(Long dietInfoId) {
        if (dietInfoId == null){
            throw new CommonException(SysErrorEnum.PARAM_ERROR, "参数错误");
        }
        DietInfo dietInfo = lambdaQuery()
                .eq(DietInfo::getId, dietInfoId)
                .one();
        if (dietInfo == null){
            throw new CommonException(SysErrorEnum.NOT_FOUND_ERROR, "用户饮食信息不存在");
        }
        return dietInfo;
    }

    @Override
    public void updateDietInfo(DietInfoUpdateDTO updateDTO) throws ParseException {
        DietInfo dietInfo = new DietInfo();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse(updateDTO.getHappenTime());
        dietInfo.setUserId(UserContext.getUserId());
        dietInfo.setId(updateDTO.getDietId());
        dietInfo.setFood(updateDTO.getFood());
        dietInfo.setType(updateDTO.getType());
        dietInfo.setHappenTime(date);
        boolean result = updateById(dietInfo);
        if (!result){
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "更新失败");
        }
    }

    @Override
    public void addDietInfo(DietInfoDTO dietInfoDTO) throws ParseException {
        DietInfo dietInfo = BeanUtil.copyProperties(dietInfoDTO, DietInfo.class);
        dietInfo.setUserId(UserContext.getUserId());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse(dietInfoDTO.getHappenTime());
        dietInfo.setHappenTime(date);
        boolean result = save(dietInfo);
        if (!result){
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "添加失败");
        }
    }

    @Override
    public Page<DietInfo> getDietInfoPage(DietInfoQueryDTO dietInfoDTO) {
        Page<DietInfo> page = page(new Page<>(dietInfoDTO.getCurrent(), dietInfoDTO.getPageSize())
                , getQueryWrapper(dietInfoDTO));
        return page;
    }

    @Override
    public QueryWrapper<DietInfo> getQueryWrapper(DietInfoQueryDTO dietInfoDTO) {
        if (ObjectUtil.isNull(dietInfoDTO)){
            throw new CommonException(SysErrorEnum.PARAM_ERROR, "请求参数为空");
        }
        Integer type = dietInfoDTO.getType();
        String food = dietInfoDTO.getFood();
        Date happenTime = dietInfoDTO.getHappenTime();
        String date = "";
        if (ObjectUtil.isNotEmpty(happenTime)){
            date = new SimpleDateFormat("yyyy-MM-dd").format(happenTime);
        }
        String sortField = dietInfoDTO.getSortField();
        String sortOrder = dietInfoDTO.getSortOrder();
        QueryWrapper<DietInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", UserContext.getUserId());
        queryWrapper.eq(type != null, "type", type);
        queryWrapper.like(StrUtil.isNotBlank(food), "food", food);
        queryWrapper.like(StrUtil.isNotBlank(date), "happen_time", date);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        return queryWrapper;
    }
}




