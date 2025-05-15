package com.naiyin.healthy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.naiyin.healthy.common.UserContext;
import com.naiyin.healthy.constant.CommonConstant;
import com.naiyin.healthy.enums.DoctorInfoExamineEnum;
import com.naiyin.healthy.enums.DoctorInfoStatusEnum;
import com.naiyin.healthy.enums.SysErrorEnum;
import com.naiyin.healthy.exception.CommonException;
import com.naiyin.healthy.model.dto.doctorInfo.*;
import com.naiyin.healthy.model.entity.DoctorExamine;
import com.naiyin.healthy.model.entity.DoctorInfo;
import com.naiyin.healthy.model.entity.User;
import com.naiyin.healthy.model.vo.DoctorInfoVO;
import com.naiyin.healthy.service.DoctorExamineService;
import com.naiyin.healthy.service.DoctorInfoService;
import com.naiyin.healthy.mapper.DoctorInfoMapper;
import com.naiyin.healthy.service.UserService;
import com.naiyin.healthy.util.SqlUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author wang'ren
* @description 针对表【doctor_info】的数据库操作Service实现
* @createDate 2025-04-16 16:42:09
*/

@Service
public class DoctorInfoServiceImpl extends ServiceImpl<DoctorInfoMapper, DoctorInfo>
    implements DoctorInfoService{

    @Resource
    private UserService userService;
    @Resource
    private DoctorExamineService doctorExamineService;
    @Resource
    private DoctorInfoMapper doctorInfoMapper;

    @Override
    public DoctorInfo getDoctorInfo(Long doctorInfoId) {
        if (doctorInfoId == null){
            throw new CommonException(SysErrorEnum.PARAM_ERROR, "参数错误");
        }
        DoctorInfo doctorInfo = lambdaQuery()
                .eq(DoctorInfo::getId, doctorInfoId)
                .one();
        if (doctorInfo == null){
            throw new CommonException(SysErrorEnum.NOT_FOUND_ERROR, "用户饮食信息不存在");
        }
        return doctorInfo;
    }

    @Override
    public void updateDoctorInfo(DoctorInfoUpdateDTO updateDTO) {
        DoctorInfo doctorInfo = BeanUtil.copyProperties(updateDTO, DoctorInfo.class);
        doctorInfo.setId(updateDTO.getDoctorId());
        boolean result = updateById(doctorInfo);
        if (!result){
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "更新失败");
        }
    }

    @Override
    public void addDoctorInfo(DoctorInfoDTO doctorInfoDTO) {
        DoctorInfo doctorInfo = BeanUtil.copyProperties(doctorInfoDTO, DoctorInfo.class);
        doctorInfo.setUserId(UserContext.getUserId());
        boolean result = save(doctorInfo);
        if (!result){
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "添加失败");
        }
    }

    @Override
    public IPage<DoctorInfoVO> getDoctorInfoPageByUser(DoctorInfoQueryDTO doctorInfoDTO) {
        long current = doctorInfoDTO.getCurrent();
        long size = doctorInfoDTO.getPageSize();
        Page<DoctorInfoVO> page = new Page<>(current, size);
        IPage<DoctorInfoVO> doctorInfoVOIPage = doctorInfoMapper.selectPassedDoctorInfo(page, doctorInfoDTO.getDoctorName());
        return doctorInfoVOIPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<DoctorInfoVO> getDoctorInfoPageByManage(DoctorInfoQueryDTO doctorInfoDTO) {
        long current = doctorInfoDTO.getCurrent();
        long size = doctorInfoDTO.getPageSize();
        String searchCondition = doctorInfoDTO.getSearchCondition();
        Page<DoctorInfoVO> page = new Page<>(current, size);
        if (StrUtil.isNotEmpty(searchCondition) && searchCondition.equals("exam")){
            IPage<DoctorInfoVO> doctorInfoVOIPage = doctorInfoMapper.selectExaminedDoctorInfo(page, doctorInfoDTO.getDoctorName());
            return doctorInfoVOIPage;
        } else if (StrUtil.isNotEmpty(searchCondition) && searchCondition.equals("notExam")) {
            IPage<DoctorInfoVO> doctorInfoVOIPage = doctorInfoMapper.selectNotExaminedDoctorInfo(page, doctorInfoDTO.getDoctorName());
            return doctorInfoVOIPage;
        }
        IPage<DoctorInfoVO> doctorInfoVOIPage = doctorInfoMapper.selectAllDoctorInfo(page, doctorInfoDTO.getDoctorName());
        return doctorInfoVOIPage;
//        Page<DoctorInfo> page = page(new Page<>(doctorInfoDTO.getCurrent(), doctorInfoDTO.getPageSize())
//                , getQueryWrapper(doctorInfoDTO));
//        Page<DoctorInfoVO> doctorInfoVOPage = new Page<>(current, size, page.getTotal());
//        List<DoctorInfo> records = page.getRecords();
//        List<DoctorInfoVO> doctorInfoVOList;
//        if (CollectionUtils.isEmpty(records)) {
//            doctorInfoVOList =  new ArrayList<>();
//        }else {
//            doctorInfoVOList = records.stream().map(this::getDoctorInfoVO).collect(Collectors.toList());
//        }
//        doctorInfoVOPage.setRecords(doctorInfoVOList);
//        return doctorInfoVOPage;
    }

    @Override
    public QueryWrapper<DoctorInfo> getQueryWrapper(DoctorInfoQueryDTO doctorInfoDTO) {
        if (ObjectUtil.isNull(doctorInfoDTO)){
            throw new CommonException(SysErrorEnum.PARAM_ERROR, "请求参数为空");
        }
        String type = doctorInfoDTO.getType();
        String searchCondition = doctorInfoDTO.getSearchCondition();
        String sortField = doctorInfoDTO.getSortField();
        String sortOrder = doctorInfoDTO.getSortOrder();
        QueryWrapper<DoctorInfo> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotEmpty(searchCondition) && searchCondition.equals("exam")){
            queryWrapper.in("status", DoctorInfoStatusEnum.PASS.getValue(), DoctorInfoStatusEnum.EXAMINE_FAILED.getValue());
        } else if (StrUtil.isNotEmpty(searchCondition) && searchCondition.equals("notExam")) {
            queryWrapper.eq("status", DoctorInfoStatusEnum.PENDING_EXAMINE.getValue());
        }
        queryWrapper.eq(type != null, "type", type);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        return queryWrapper;
    }

    public DoctorInfoVO getDoctorInfoVO(DoctorInfo doctorInfo) {
        if (doctorInfo == null) {
            return null;
        }
        DoctorInfoVO doctorInfoVO = BeanUtil.copyProperties(doctorInfo, DoctorInfoVO.class);
        User user = userService.lambdaQuery().eq(User::getId, doctorInfo.getUserId()).one();
        doctorInfoVO.setUserId(user.getId());
        doctorInfoVO.setUsername(user.getUsername());
        doctorInfoVO.setUserAvatar(user.getUserAvatar());
        return doctorInfoVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void examineDoctorInfo(DoctorInfoExamineDTO doctorInfoExamineDTO) {
        DoctorExamine doctorInfoExamine = new DoctorExamine();
        Long doctorInfoId = doctorInfoExamineDTO.getDoctorInfoId();
        String result = doctorInfoExamineDTO.getResult();
        String description = doctorInfoExamineDTO.getDescription();
        if (result.equals(DoctorInfoExamineEnum.NOT_PASS.getText())){
            if (StrUtil.isBlank(description)){
                throw new CommonException(SysErrorEnum.PARAM_ERROR, "请填写驳回原因");
            }
            doctorInfoExamine.setDoctorInfoId(doctorInfoId);
            doctorInfoExamine.setResult(DoctorInfoExamineEnum.NOT_PASS.getValue());
            doctorInfoExamine.setDescription(description);
            boolean save = doctorExamineService.save(doctorInfoExamine);
            if (!save){
                throw new CommonException(SysErrorEnum.OPERATION_ERROR, "审核失败");
            }
            DoctorInfo doctorInfo = new DoctorInfo();
            doctorInfo.setId(doctorInfoId);
            doctorInfo.setStatus(DoctorInfoStatusEnum.EXAMINE_FAILED.getValue());
            boolean update = updateById(doctorInfo);
            if (!update){
                throw new CommonException(SysErrorEnum.OPERATION_ERROR, "更新失败");
            }
        }
        if (result.equals(DoctorInfoExamineEnum.PASS.getText())){
            doctorInfoExamine.setDoctorInfoId(doctorInfoId);
            doctorInfoExamine.setResult(DoctorInfoExamineEnum.PASS.getValue());
            doctorInfoExamine.setDescription(description);
            boolean save = doctorExamineService.save(doctorInfoExamine);
            if (!save){
                throw new CommonException(SysErrorEnum.OPERATION_ERROR, "审核失败");
            }
            DoctorInfo doctorInfo = new DoctorInfo();
            doctorInfo.setId(doctorInfoId);
            doctorInfo.setStatus(DoctorInfoStatusEnum.PASS.getValue());
            boolean update = updateById(doctorInfo);
            if (!update){
                throw new CommonException(SysErrorEnum.OPERATION_ERROR, "更新失败");
            }
        }
    }

    @Override
    public DoctorInfo getDoctorInfoByUserId(GetDoctorInfoDTO getDoctorInfoDTO) {
        if (ObjectUtil.isNull(getDoctorInfoDTO.getUserId())){
            throw new CommonException(SysErrorEnum.PARAM_ERROR, "参数错误");
        }
        DoctorInfo doctorInfo = lambdaQuery()
                .eq(DoctorInfo::getUserId, getDoctorInfoDTO.getUserId())
                .one();
        if (doctorInfo == null){
            throw new CommonException(SysErrorEnum.NOT_FOUND_ERROR, "信息不存在");
        }
        return doctorInfo;
    }

}




