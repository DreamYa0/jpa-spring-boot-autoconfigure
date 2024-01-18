package com.g7.framework.jpa.dynamic.datasource;

import java.lang.annotation.*;

/**
 * Specify DataSource (指定数据源)<br>
 * 注意：<br>
 * {@code @AssignDataSource}放在类级别上等同于该类的每个公有方法都放上了{@code @AssignDataSource}<br>
 * {@code @AssignDataSource}只对公有法有效，因为都是Spring AOP代理）
 * @author dreamyao
 * @title
 * @date 2021/11/14 下午20:43
 * @since 1.0.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface SpecifyDataSource {

    /**
     * 数据源的名称
     */
    String value() default "";
}
