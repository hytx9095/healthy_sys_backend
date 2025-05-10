package com.naiyin.healthy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.naiyin.healthy.model.dto.doctorInfo.DoctorInfoDTO;
import com.naiyin.healthy.model.dto.doctorInfo.DoctorInfoExamineDTO;
import com.naiyin.healthy.model.dto.doctorInfo.DoctorInfoQueryDTO;
import com.naiyin.healthy.model.dto.doctorInfo.DoctorInfoUpdateDTO;
import com.naiyin.healthy.model.entity.DoctorInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.naiyin.healthy.model.vo.DoctorInfoVO;

import java.text.ParseException;
import java.util.List;

/**
* @author wang'ren
* @description 针对表【doctor_info】的数据库操作Service
* @createDate 2025-04-16 16:42:09
*/
public interface DoctorInfoService extends IService<DoctorInfo> {

    DoctorInfo getDoctorInfo(Long doctorInfoId);

    void updateDoctorInfo(DoctorInfoUpdateDTO updateDTO);

    void addDoctorInfo(DoctorInfoDTO doctorInfoDTO) throws ParseException;

    IPage<DoctorInfoVO> getDoctorInfoPageByUser(DoctorInfoQueryDTO doctorInfoDTO);

    IPage<DoctorInfoVO> getDoctorInfoPageByManage(DoctorInfoQueryDTO doctorInfoDTO);

    QueryWrapper<DoctorInfo> getQueryWrapper(DoctorInfoQueryDTO doctorInfoDTO);
    DoctorInfoVO getDoctorInfoVO(DoctorInfo doctorInfo);

    void examineDoctorInfo(DoctorInfoExamineDTO doctorInfoExamineDTO);
}
