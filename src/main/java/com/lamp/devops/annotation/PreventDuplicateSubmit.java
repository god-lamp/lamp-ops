package com.lamp.devops.annotation;

import java.lang.annotation.*;

/**
 * @author god-lamp
 * @since 2024-02-20
 * 防止重复提交注解
 */
@Inherited
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PreventDuplicateSubmit {
    /**
     * 防重提交锁过期时间(秒); 默认 5 秒内不允许重复提交
     */
    long expire() default 5;
}
