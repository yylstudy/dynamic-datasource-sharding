package com.yyl.sharding;

import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.yyl.dynamicds.LHikariDataSourceCreator;
import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;
import org.apache.shardingsphere.sharding.api.config.ShardingRuleConfiguration;
import org.apache.shardingsphere.sharding.yaml.config.YamlShardingRuleConfiguration;
import org.apache.shardingsphere.sharding.yaml.swapper.YamlShardingRuleConfigurationSwapper;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yang.yonglian
 * @version 1.0.0
 * @Description TODO
 * @createTime 2023/9/11 14:51
 */

public class ShardingLHikariDataSourceCreator extends LHikariDataSourceCreator {
    private YamlShardingRuleConfiguration yamlShardingRuleConfiguration;
    private YamlShardingRuleConfigurationSwapper yamlShardingStrategyConfigurationSwapper = new YamlShardingRuleConfigurationSwapper();

    public ShardingLHikariDataSourceCreator(YamlShardingRuleConfiguration yamlShardingRuleConfiguration) {
        this.yamlShardingRuleConfiguration = yamlShardingRuleConfiguration;
    }
    @Override
    public DataSource doCreateDataSource(DataSourceProperty dataSourceProperty) {
        try{
            DataSource lHikariDataSource = super.doCreateDataSource(dataSourceProperty);
            Map<String, DataSource> dataSourceMap = new HashMap<>();
            String databaseName = "sharding-datasource";
            dataSourceMap.put(databaseName,lHikariDataSource);
            ShardingRuleConfiguration shardingRuleConfiguration = yamlShardingStrategyConfigurationSwapper.swapToObject(yamlShardingRuleConfiguration);
            DataSource dataSource = ShardingSphereDataSourceFactory.createDataSource(databaseName, null,
                    dataSourceMap, Arrays.asList(shardingRuleConfiguration), null);
            return dataSource;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
