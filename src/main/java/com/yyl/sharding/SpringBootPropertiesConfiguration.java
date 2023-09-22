package com.yyl.sharding;

import lombok.Getter;
import lombok.Setter;
import org.apache.shardingsphere.infra.yaml.config.pojo.mode.YamlModeConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Properties;

/**
 * @author yang.yonglian
 * @version 1.0.0
 * @Description TODO
 * @createTime 2023/9/22 10:47
 */
@ConfigurationProperties(prefix = "spring.shardingsphere")
@Getter
@Setter
public class SpringBootPropertiesConfiguration {
    private Properties props = new Properties();

    private YamlModeConfiguration mode;
}
