package com.cctc.fast.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 业务日志注解
 * @author Hejeo
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BussinessLog {
	/**
     * 业务的名称,例如:"添加菜单"
     */
    String value() default "";
}
