package com.g7.framework.jpa.dynamic.loadbalance;

import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Set;

/**
 * @author dreamyao
 * @title
 * @date 2021/11/14 下午20:43
 * @since 1.0.0
 */
public abstract class AbstractLoadBalance implements LoadBalance {

    @Override
    public String select(Map<String, DataSource> dataSources) {
        if (CollectionUtils.isEmpty(dataSources)) {
            return null;
        }

        if (dataSources.size() == 1) {
            Set<String> keySet = dataSources.keySet();
            return keySet.toArray(new String[1])[0];
        }
        return doSelect(dataSources);
    }

    protected abstract String doSelect(Map<String, DataSource> dataSources);

    int random(int num) {
        return (int) (Math.random() * num);
    }
}
