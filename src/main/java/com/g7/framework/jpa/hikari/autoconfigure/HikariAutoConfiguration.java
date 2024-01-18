package com.g7.framework.jpa.hikari.autoconfigure;

import com.g7.framework.jpa.dynamic.datasource.DynamicDataSource;
import com.g7.framework.jpa.hikari.autoconfigure.properties.HikariDataSourceProperties;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static com.g7.framework.jpa.hikari.autoconfigure.properties.HikariConstants.MASTER_DATASOURCE_NAME;


/**
 * Hikari 连接池的自动配置
 * @author dreamyao
 */
@Configuration
@ConditionalOnClass(HikariDataSource.class)
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
@EnableConfigurationProperties({DataSourceProperties.class, HikariDataSourceProperties.class})
@Import({HikariDataSourceConfiguration.class})
public class HikariAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(HikariAutoConfiguration.class);

    @Primary
    @Resource
    @Bean("dataSource")
    @ConditionalOnMissingBean(value = {DynamicDataSource.class})
    public DynamicDataSource dataSource(Map<String, HikariDataSource> dataSources) {
        logger.debug("dynamic datasource init...");
        Assert.notEmpty(dataSources, "master data source is null.");

        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        DataSource defaultDataSource = null;
        for (Map.Entry<String, HikariDataSource> entry : dataSources.entrySet()) {

            if (dataSources.size() == 1) {
                defaultDataSource = entry.getValue();
                break;
            }

            if (entry.getKey().toLowerCase().contains(MASTER_DATASOURCE_NAME)) {
                defaultDataSource = entry.getValue();
                break;
            }
        }

        // 确保数据源至少存在主库数据源
        Assert.notNull(defaultDataSource, "default dataSource is null.");

        Map<Object, Object> map = new HashMap<>(dataSources);

        // 加入所有数据源
        dynamicDataSource.setTargetDataSources(map);
        // 加入默认数据源
        dynamicDataSource.setDefaultTargetDataSource(defaultDataSource);
        return dynamicDataSource;
    }
}