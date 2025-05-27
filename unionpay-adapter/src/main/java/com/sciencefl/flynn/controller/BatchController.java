package com.sciencefl.flynn.controller;


import cn.hutool.json.JSONUtil;
import com.sciencefl.flynn.aspect.AntiReplay;
import com.sciencefl.flynn.common.BaseResponse;
import com.sciencefl.flynn.dto.ApplyDTO;
import com.sciencefl.flynn.dto.BaseRequest;
import com.sciencefl.flynn.service.mq.ProducerService;
import com.sciencefl.flynn.util.ValidationUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/ssc/batches")
public class BatchController {

    @Autowired
    ProducerService producerService;

    @AntiReplay(expireTime = 60)  // 60秒内禁止重复请求
    @PreAuthorize("hasAuthority('SCOPE_push_batchdata')")
    @PostMapping("/push_data")
    public BaseResponse<String> createBatch( @Valid @RequestBody BaseRequest baseDTO) {
        if(baseDTO.getOperation().equals("APPLY")){
            String jsonStr = JSONUtil.toJsonStr(baseDTO.getData());
            List<ApplyDTO> list =  JSONUtil.toList(jsonStr, ApplyDTO.class);
            ValidationUtil.validate(list);
            log.info("接收到批量数据：{}", JSONUtil.toJsonStr(list));
            for (ApplyDTO applyDTO : list) {
                // 这里可以添加更多的业务逻辑处理
               producerService.sendToTopicB(applyDTO);
            }
        }

        return BaseResponse.success("ok");
    }
}
