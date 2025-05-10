package com.naiyin.healthy.mapper;

import com.naiyin.healthy.model.entity.DietInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author wang'ren
* @description 针对表【diet_info】的数据库操作Mapper
* @createDate 2024-12-09 17:05:04
* @Entity com.naiyin.healthy.model.entity.DietInfo
*/
public interface DietInfoMapper extends BaseMapper<DietInfo> {

    @Select("SELECT * FROM diet_info " +
            "WHERE user_id = #{userId} " +
            "  AND happen_time >= DATE_SUB(CURRENT_DATE(), INTERVAL 7 DAY) " +
            "  AND is_delete = 0 " +
            "ORDER BY happen_time DESC")
    List<DietInfo> selectRecentDietsByUserId(@Param("userId") Long userId);
}




