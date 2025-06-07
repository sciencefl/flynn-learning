package com.sciencefl.flynn.service.processor;

import com.sciencefl.flynn.dao.UnionPayApplyDao;
import com.sciencefl.flynn.dao.entity.UnionPayApply;
import com.sciencefl.flynn.dto.ApplyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class DatabaseMessageProcessor {

    @Autowired
    private UnionPayApplyDao unionPayApplyDao;

    public void processMessage(ApplyDTO data) {
        // 使用Spring事务管理
        UnionPayApply unionPayApply = convertToEntity(data);
        unionPayApplyDao.save(unionPayApply);
    }

    private UnionPayApply convertToEntity(ApplyDTO data) {
        return UnionPayApply.builder()
            .userId(data.getUserId())
            .couponId(data.getCouponId())
            .ageRange(data.getAgeRange())
            .bankAccount(data.getBankAccount())
            .amount(data.getAmount())
            .test(data.getTest())
            .build();
    }
}
