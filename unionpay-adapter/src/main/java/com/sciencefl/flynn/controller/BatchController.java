package com.sciencefl.flynn.controller;


import cn.hutool.json.JSONUtil;
import com.sciencefl.flynn.aspect.AntiReplay;
import com.sciencefl.flynn.common.Result;
import com.sciencefl.flynn.common.ResultCode;
import com.sciencefl.flynn.dto.ApplyDTO;
import com.sciencefl.flynn.dto.BaseRequest;
import com.sciencefl.flynn.exception.BusinessException;
import com.sciencefl.flynn.exception.ValidationException;
import com.sciencefl.flynn.service.mq.ProducerService;
import com.sciencefl.flynn.util.ValidationUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/ssc/batches")
public class BatchController {

    @Autowired
    ProducerService producerService;

    @AntiReplay(expireTime = 60)  // 60秒内禁止重复请求
    // @PreAuthorize("hasAuthority('SCOPE_push_batchdata')") // 需要在配置文件中定义SCOPE_push_batchdata权限
    @PostMapping("/push_data")
    public Result<String> createBatch(@Valid @RequestBody BaseRequest baseDTO) {
        if (!"APPLY".equals(baseDTO.getOperation())) {
            throw new ValidationException("不支持的操作类型");
        }

        List<ApplyDTO> dataList;
        try {
            String jsonStr = JSONUtil.toJsonStr(baseDTO.getData());
            dataList = JSONUtil.toList(jsonStr, ApplyDTO.class);
        } catch (Exception e) {
            throw new ValidationException("数据格式转换失败："+e.getMessage());
        }

        if (dataList == null || dataList.isEmpty()) {
            throw new ValidationException("批量数据不能为空");
        }

        try {
            ValidationUtil.validate(dataList);
        } catch (Exception e) {
            throw new ValidationException("数据校验失败: " + e.getMessage());
        }

        log.info("接收到批量数据：{}", JSONUtil.toJsonStr(dataList));

        try {
            for (ApplyDTO applyDTO : dataList) {
                producerService.sendToTopicB(applyDTO);
            }
        } catch (Exception e) {
            throw new BusinessException(ResultCode.BUSINESS_ERROR, "消息发送失败: " + e.getMessage(),
                    Map.of("data", dataList));
        }

        return Result.success("处理成功");
    }
}
