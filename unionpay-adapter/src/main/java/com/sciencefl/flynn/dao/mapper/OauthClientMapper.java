package com.sciencefl.flynn.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sciencefl.flynn.dao.entity.UnionPayOauthClient;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OauthClientMapper extends BaseMapper<UnionPayOauthClient> {
}
