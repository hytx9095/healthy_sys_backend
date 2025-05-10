package com.naiyin.healthy.mapper;

import com.naiyin.healthy.model.entity.DiseaseInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author wang'ren
* @description 针对表【disease_info】的数据库操作Mapper
* @createDate 2025-04-15 14:44:12
* @Entity com.naiyin.healthy.model.entity.DiseaseInfo
*/
public interface DiseaseInfoMapper extends BaseMapper<DiseaseInfo> {

    @Select("SELECT * FROM disease_info WHERE user_id = #{userId} AND happen_time >= DATE_SUB(NOW(), INTERVAL 7 DAY) AND is_delete = 0")
    List<DiseaseInfo> selectRecentDiseaseInfoByUserId(@Param("userId") Long userId);
}




