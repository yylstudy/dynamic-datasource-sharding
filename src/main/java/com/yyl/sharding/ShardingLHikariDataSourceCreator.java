package com.yyl.sharding;

import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.yyl.dynamicds.LHikariDataSourceCreator;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ShardingLHikariDataSourceCreator extends LHikariDataSourceCreator {
    private YamlShardingRuleConfiguration yamlShardingRuleConfiguration;
    private YamlShardingRuleConfigurationSwapper yamlShardingStrategyConfigurationSwapper = new YamlShardingRuleConfigurationSwapper();
    private SpringBootPropertiesConfiguration springBootPropertiesConfiguration;

    public ShardingLHikariDataSourceCreator(YamlShardingRuleConfiguration yamlShardingRuleConfiguration,
                                            SpringBootPropertiesConfiguration springBootPropertiesConfiguration) {
        this.yamlShardingRuleConfiguration = yamlShardingRuleConfiguration;
        this.springBootPropertiesConfiguration = springBootPropertiesConfiguration;
    }
    @Override
    public DataSource doCreateDataSource(DataSourceProperty dataSourceProperty) {
        try{
            long t1 = System.currentTimeMillis();
            DataSource lHikariDataSource = super.doCreateDataSource(dataSourceProperty);
            Map<String, DataSource> dataSourceMap = new HashMap<>();
            String databaseName = "sharding-datasource";
            dataSourceMap.put(databaseName,lHikariDataSource);
            ShardingRuleConfiguration shardingRuleConfiguration = yamlShardingStrategyConfigurationSwapper.swapToObject(yamlShardingRuleConfiguration);
            DataSource dataSource = ShardingSphereDataSourceFactory.createDataSource(databaseName, null,
                    dataSourceMap, Arrays.asList(shardingRuleConfiguration), springBootPropertiesConfiguration.getProps());
            long t2 = System.currentTimeMillis();
            log.info("create sharding datasource time:{}",t2-t1);
            return dataSource;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
