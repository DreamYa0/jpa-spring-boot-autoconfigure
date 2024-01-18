package com.g7.framework.jpa.hikari.autoconfigure.listener;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

import java.util.Map;

/**
 * @author dreamyao
 * @title
 * @date 2021/11/14 下午20:43
 * @since 1.0.0
 */
public class ShutdownHookListener implements ApplicationListener<ContextClosedEvent> {

    private final Map<String, HikariDataSource> dataSources;

    public ShutdownHookListener(Map<String, HikariDataSource> dataSources) {
        this.dataSources = dataSources;
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        HikariShutdownHook hikariShutdownHook = HikariShutdownHook.getDruidShutdownHook(dataSources);
        hikariShutdownHook.start();
    }
}
