package com.sciencefl.flynn.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sciencefl.flynn.dao.entity.UnionPayOauthClient;
import com.sciencefl.flynn.dao.mapper.OauthClientMapper;
import org.springframework.stereotype.Service;

@Service
public class OauthClientDao extends ServiceImpl<OauthClientMapper, UnionPayOauthClient> {
    // 已经可以使用各种内置的 CRUD 方法
}

