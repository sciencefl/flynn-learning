package com.sciencefl.flynn.common;

     import lombok.Getter;

     @Getter
     public enum ResultCode {
         // 通用结果码 (1xx)
         SUCCESS(200, "操作成功"),

         // 客户端错误 (4xx)
         PARAM_ERROR(400, "参数错误"),
         UNAUTHORIZED(401, "未授权"),
         FORBIDDEN(403, "访问被拒绝"),
         NOT_FOUND(404, "资源不存在"),
         METHOD_NOT_ALLOWED(405, "请求方法不允许"),
         ANTI_REPLAY_ERROR(429, "请求过于频繁"),

         // OAuth2相关错误 (5xx)
         INVALID_CLIENT(501, "无效的客户端"),
         INVALID_TOKEN(502, "无效的令牌"),
         EXPIRED_TOKEN(503, "令牌已过期"),
         INSUFFICIENT_SCOPE(504, "权限不足"),

         // 业务错误 (6xx)
         BUSINESS_ERROR(600, "业务处理失败"),
         DATA_NOT_FOUND(601, "数据不存在"),
         DATA_DUPLICATE(602, "数据重复"),
         INVALID_STATUS(603, "状态异常"),

         // 系统错误 (9xx)
         INTERNAL_ERROR(900, "服务器内部错误"),
         SERVICE_UNAVAILABLE(901, "服务不可用"),
         DATABASE_ERROR(902, "数据库错误"),
         REDIS_ERROR(903, "缓存服务错误"),
         RPC_ERROR(904, "远程调用错误");

         private final int code;
         private final String message;

         ResultCode(int code, String message) {
             this.code = code;
             this.message = message;
         }
     }