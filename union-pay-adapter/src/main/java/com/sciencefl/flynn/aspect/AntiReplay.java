package com.sciencefl.flynn.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AntiReplay {
    String keyPrefix() default "anti_replay:";  // Redis键前缀
    long expireTime() default 30;               // 过期时间（秒）
}
