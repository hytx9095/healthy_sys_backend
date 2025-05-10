package com.naiyin.healthy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.naiyin.healthy.model.entity.UserTags;
import com.naiyin.healthy.service.UserTagsService;
import com.naiyin.healthy.mapper.UserTagsMapper;
import org.springframework.stereotype.Service;

/**
* @author wang'ren
* @description 针对表【user_tags】的数据库操作Service实现
* @createDate 2025-05-10 14:40:30
*/
@Service
public class UserTagsServiceImpl extends ServiceImpl<UserTagsMapper, UserTags>
    implements UserTagsService{

}




