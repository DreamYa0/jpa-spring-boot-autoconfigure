package com.g7.framework.jpa.dynamic.datasource;

import java.lang.annotation.*;

/**
 * @author dreamyao
 * @title 用于标识Spring Data Jpa Repository的方法选择主库数据源
 * @date 2018/8/19 上午12:11
 * @since 1.0.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Master {
}
