package com.naiyin.healthy.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.naiyin.healthy.model.entity.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.naiyin.healthy.model.vo.MessageVO;
import org.apache.ibatis.annotations.Param;

/**
* @author wang'ren
* @description 针对表【message】的数据库操作Mapper
* @createDate 2024-12-06 11:19:44
* @Entity com.naiyin.healthy.model.entity.Message
*/
public interface MessageMapper extends BaseMapper<Message> {

    IPage<MessageVO> getUserMessages(Page<MessageVO> page,
                                     @Param("userId") Long userId);

    IPage<MessageVO> getMessagePage(Page<MessageVO> page, @Param("content") String content);

    IPage<MessageVO> getMessagePageByStatus(Page<MessageVO> page,
                                            @Param("status") Integer status,
                                            @Param("content") String content);
}




