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
import com.naiyin.healthy.model.dto.dietInfo.DietInfoQueryDTO;
import com.naiyin.healthy.model.dto.sportInfo.SportInfoDTO;
import com.naiyin.healthy.model.dto.sportInfo.SportInfoQueryDTO;
import com.naiyin.healthy.model.dto.sportInfo.SportInfoUpdateDTO;
import com.naiyin.healthy.model.entity.DietInfo;
import com.naiyin.healthy.model.entity.SportInfo;
import com.naiyin.healthy.rabbitmq.MQMessageProducer;
import com.naiyin.healthy.service.SportInfoService;
import com.naiyin.healthy.mapper.SportInfoMapper;
import com.naiyin.healthy.util.SqlUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
* @author wang'ren
* @description 针对表【sport_info】的数据库操作Service实现
* @createDate 2024-12-09 17:05:18
*/
@Service
public class SportInfoServiceImpl extends ServiceImpl<SportInfoMapper, SportInfo>
    implements SportInfoService{

    @Resource
    private MQMessageProducer mqMessageProducer;
    @Override
    public SportInfo getSportInfo(Long sportInfoId) {
        SportInfo sportInfo = lambdaQuery()
                .eq(SportInfo::getId, sportInfoId)
                .one();
        if (sportInfo == null){
            throw new CommonException(SysErrorEnum.NOT_FOUND_ERROR, "用户运动信息不存在");
        }
        return sportInfo;
    }

    @Override
    public void updateSportInfo(SportInfoUpdateDTO updateDTO) throws ParseException {
        SportInfo sportInfo = new SportInfo();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse(updateDTO.getOccurrenceTime());
        sportInfo.setUserId(UserContext.getUserId());
        sportInfo.setId(updateDTO.getSportInfoId());
        sportInfo.setContent(updateDTO.getContent());
        sportInfo.setOccurrenceTime(date);
        boolean result = updateById(sportInfo);
        if (!result){
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "更新失败");
        }
    }

    @Override
    public void addSportInfo(SportInfoDTO sportInfoDTO) throws ParseException {
        SportInfo sportInfo = BeanUtil.copyProperties(sportInfoDTO, SportInfo.class);
        sportInfo.setUserId(UserContext.getUserId());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse(sportInfoDTO.getOccurrenceTime());
        sportInfo.setOccurrenceTime(date);
        boolean result = save(sportInfo);
        if (!result){
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "添加失败");
        }
    }

    @Override
    public Page<SportInfo> getSportInfoPage(SportInfoQueryDTO queryDTO) {
        Page<SportInfo> page = page(new Page<>(queryDTO.getCurrent(), queryDTO.getPageSize())
                , getQueryWrapper(queryDTO));
        return page;
    }

    @Override
    public QueryWrapper<SportInfo> getQueryWrapper(SportInfoQueryDTO queryDTO) {
        if (ObjectUtil.isNull(queryDTO)){
            throw new CommonException(SysErrorEnum.PARAM_ERROR, "请求参数为空");
        }
        String content = queryDTO.getContent();
        Date happenTime = queryDTO.getOccurrenceTime();
        String date = "";
        if (ObjectUtil.isNotEmpty(happenTime)){
            date = new SimpleDateFormat("yyyy-MM-dd").format(happenTime);
        }
        String sortField = queryDTO.getSortField();
        String sortOrder = queryDTO.getSortOrder();
        QueryWrapper<SportInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", UserContext.getUserId());
        queryWrapper.like(StrUtil.isNotBlank(content), "content", content);
        queryWrapper.like(StrUtil.isNotBlank(date), "occurrence_time", date);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        return queryWrapper;
    }
    @Override
    public void generateSuggestions() {
        // TODO:
    }
}




