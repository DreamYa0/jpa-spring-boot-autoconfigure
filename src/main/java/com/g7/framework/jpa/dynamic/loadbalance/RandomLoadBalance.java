package com.g7.framework.jpa.dynamic.loadbalance;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author dreamyao
 * @title
 * @date 2021/11/14 下午20:43
 * @since 1.0.0
 */
public class RandomLoadBalance extends AbstractLoadBalance {

    public static final String NAME = "random";

    @Override
    protected String doSelect(Map<String, DataSource> dataSources) {

        List<String> dataSourceNames = new ArrayList<>(dataSources.keySet());

        return dataSourceNames.get(random(dataSourceNames.size()));
    }
}
