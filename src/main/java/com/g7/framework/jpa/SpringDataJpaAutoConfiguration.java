package com.g7.framework.jpa;

import com.g7.framework.jpa.dynamic.datasource.AutoDataSourceAspect;
import com.g7.framework.jpa.dynamic.datasource.SpecifyDataSourceAspect;
import com.g7.framework.jpa.hikari.autoconfigure.listener.DataSourceChangeListener;
import com.g7.framework.jpa.hikari.autoconfigure.listener.ShutdownHookListener;
import com.g7.framework.jpa.hikari.autoconfigure.properties.HikariDataSourceProperties;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import java.util.Map;


/**
 * @author dreamyao
 * @title jpa 自动化配置类
 * @date 2021/11/14 下午20:43
 * @since 1.0.0
 */
@Configuration
@EnableJpaAuditing
@EnableConfigurationProperties({HikariDataSourceProperties.class})
@EnableTransactionManagement
@EntityScan(basePackages = "com.**.dao.entity")
@EnableJpaRepositories(basePackages = "com.**.dao.repository")
public class SpringDataJpaAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(SpringDataJpaAutoConfiguration.class);
    private final HikariDataSourceProperties hikariProperties;
    private final PlatformTransactionManager platformTransactionManager;
    private final EntityManager entityManager;

    public SpringDataJpaAutoConfiguration(HikariDataSourceProperties hikariProperties,
                                          PlatformTransactionManager platformTransactionManager,
                                          EntityManager entityManager) {
        this.hikariProperties = hikariProperties;
        this.platformTransactionManager = platformTransactionManager;
        this.entityManager = entityManager;
    }

    @Bean
    @ConditionalOnClass(value = SpecifyDataSourceAspect.class)
    @ConditionalOnMissingBean(value = {SpecifyDataSourceAspect.class})
    public SpecifyDataSourceAspect specifyDataSourceAspect() {
        logger.debug("specify datasource aspect init...");
        return new SpecifyDataSourceAspect(hikariProperties);
    }

    @Bean
    @ConditionalOnClass(value = AutoDataSourceAspect.class)
    @ConditionalOnMissingBean(value = {AutoDataSourceAspect.class})
    public AutoDataSourceAspect autoDataSourceAspect() {
        logger.debug("specify datasource aspect init...");
        return new AutoDataSourceAspect(hikariProperties, platformTransactionManager);
    }

    @Bean
    public ShutdownHookListener shutdownHookListener(Map<String, HikariDataSource> dataSources) {
        logger.debug("druid shutdown hook listener init...");
        return new ShutdownHookListener(dataSources);
    }

    @Bean
    public DataSourceChangeListener dataSourceChangeListener() {
        return new DataSourceChangeListener();
    }

    @Bean
    @ConditionalOnClass(value = JPAQueryFactory.class)
    @ConditionalOnMissingBean(value = JPAQueryFactory.class)
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}
