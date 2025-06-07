package com.sciencefl.flynn.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sciencefl.flynn.dao.entity.UnionPayApply;
import com.sciencefl.flynn.dao.mapper.UnionPayApplyMapper;
import org.springframework.stereotype.Service;

@Service
public class UnionPayApplyDao extends ServiceImpl<UnionPayApplyMapper, UnionPayApply> {
    // 已经可以使用各种内置的 CRUD 方法
}
