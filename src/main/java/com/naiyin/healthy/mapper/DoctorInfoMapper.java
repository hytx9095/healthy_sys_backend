package com.naiyin.healthy.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.naiyin.healthy.model.entity.DoctorInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.naiyin.healthy.model.vo.DoctorInfoVO;
import org.apache.ibatis.annotations.Param;

/**
* @author wang'ren
* @description 针对表【doctor_info】的数据库操作Mapper
* @createDate 2025-04-16 16:42:09
* @Entity com.naiyin.healthy.model.entity.DoctorInfo
*/
public interface DoctorInfoMapper extends BaseMapper<DoctorInfo> {

    IPage<DoctorInfoVO> selectAllDoctorInfo(Page<DoctorInfoVO> page,
                                            @Param("doctorName") String doctorName);

    IPage<DoctorInfoVO> selectPassedDoctorInfo(Page<DoctorInfoVO> page,
                                               @Param("doctorName") String doctorName);

    IPage<DoctorInfoVO> selectExaminedDoctorInfo(Page<DoctorInfoVO> page,
                                                 @Param("doctorName") String doctorName);

    IPage<DoctorInfoVO> selectNotExaminedDoctorInfo(Page<DoctorInfoVO> page,
                                                 @Param("doctorName") String doctorName);
}




