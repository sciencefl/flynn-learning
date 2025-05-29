package com.sciencefl.flynn.common;

     import lombok.Getter;

     @Getter
     public enum ResultCode {
         SUCCESS(200, "操作成功"),
         PARAM_ERROR(400, "参数错误"),
         UNAUTHORIZED(401, "未授权"),
         FORBIDDEN(403, "访问被拒绝"),
         NOT_FOUND(404, "资源不存在"),
         INTERNAL_ERROR(500, "服务器内部错误"),
         BUSINESS_ERROR(510, "业务处理失败"),
         ANTI_REPLAY_ERROR(429, "请求过于频繁");

         private final int code;
         private final String message;

         ResultCode(int code, String message) {
             this.code = code;
             this.message = message;
         }
     }