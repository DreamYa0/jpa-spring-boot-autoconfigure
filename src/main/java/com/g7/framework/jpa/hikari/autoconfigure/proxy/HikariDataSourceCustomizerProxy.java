package com.g7.framework.jpa.hikari.autoconfigure.proxy;


import com.g7.framework.jpa.hikari.customizer.HikariDataSourceCustomizer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author dreamyao
 * @title
 * @date 2021/11/14 下午20:43
 * @since 1.0.0
 */
public class HikariDataSourceCustomizerProxy implements InvocationHandler {

    private final HikariDataSourceCustomizer hikariDataSourceCustomizer;

    public HikariDataSourceCustomizerProxy(HikariDataSourceCustomizer hikariDataSourceCustomizer) {
        this.hikariDataSourceCustomizer = hikariDataSourceCustomizer;
    }

    /**
     * 绑定委托对象并返回一个【代理占位】
     * @param hikariDataSourceCustomizer 数据源定制器
     * @return 代理占位
     */
    public static Object bind(HikariDataSourceCustomizer hikariDataSourceCustomizer) {
        return Proxy.newProxyInstance(hikariDataSourceCustomizer.getClass().getClassLoader(),
                new Class[]{HikariDataSourceCustomizer.class},
                new HikariDataSourceCustomizerProxy(hikariDataSourceCustomizer));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(proxy, args);
    }
}
