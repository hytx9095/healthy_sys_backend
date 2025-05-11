package com.naiyin.healthy.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.naiyin.healthy.model.entity.HealthyKnowledge;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.naiyin.healthy.model.vo.HealthyKnowledgeVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author wang'ren
* @description 针对表【healthy_knowledge】的数据库操作Mapper
* @createDate 2024-12-09 17:05:30
* @Entity com.naiyin.healthy.model.entity.HealthyKnowledge
*/
public interface HealthyKnowledgeMapper extends BaseMapper<HealthyKnowledge> {

    IPage<HealthyKnowledgeVO> getHealthyKnowledgePage(Page<HealthyKnowledgeVO> page,
                                                      @Param("userId") Long userId,
                                                      @Param("status") Integer status);

    IPage<HealthyKnowledgeVO> getHealthyKnowledgePageByContent(Page<HealthyKnowledgeVO> page,
                                                      @Param("userId") Long userId,
                                                      @Param("status") Integer status,
                                                      @Param("content") String content);

    IPage<HealthyKnowledgeVO> getStarredHealthyKnowledgePage(Page<HealthyKnowledgeVO> page,
                                                      @Param("userId") Long userId,
                                                      @Param("status") Integer status);

    // Mapper 接口
    List<HealthyKnowledgeVO> selectByTagList(@Param("tags") List<String> tags);

    List<HealthyKnowledgeVO> selectNewHealthyKnowledgeList(@Param("num") Integer num);
}




