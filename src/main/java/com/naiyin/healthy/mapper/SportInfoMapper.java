package com.naiyin.healthy.mapper;

import com.naiyin.healthy.model.entity.SportInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author wang'ren
* @description 针对表【sport_info】的数据库操作Mapper
* @createDate 2024-12-09 17:05:18
* @Entity com.naiyin.healthy.model.entity.SportInfo
*/
public interface SportInfoMapper extends BaseMapper<SportInfo> {
    @Select("SELECT * FROM sport_info WHERE user_id = #{userId} AND occurrence_time >= DATE_SUB(NOW(), INTERVAL 7 DAY) AND is_delete = 0")
    List<SportInfo> selectRecentSportInfoByUserId(@Param("userId") Long userId);
}




