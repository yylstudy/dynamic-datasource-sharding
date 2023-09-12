package com.yyl.dynamicds;

import com.baomidou.dynamic.datasource.provider.AbstractDataSourceProvider;
import com.baomidou.dynamic.datasource.provider.DynamicDataSourceProvider;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import com.linkcircle.ss.F;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yang.yonglian
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022/4/14 14:20
 */
@Slf4j
public class JdbcDataSourceProvider extends AbstractDataSourceProvider implements DynamicDataSourceProvider {

    private DynamicDataSourceProperties properties;
    /**
     * JDBC driver
     */
    private final String driverClassName;
    /**
     * JDBC url 地址
     */
    private final String url;
    /**
     * JDBC 用户名
     */
    private final String username;
    /**
     * JDBC 密码
     */
    private final String password;
    @Autowired
    private DataSourcePropertyConverter dataSourcePropertyConverter;

    public JdbcDataSourceProvider(String driverClassName,String url, String username, String password, DynamicDataSourceProperties properties) {
        this.driverClassName = driverClassName;
        this.url = url;
        this.username = username;
        this.password = password;
        this.properties = properties;
    }

    @Override
    public Map<String, DataSource> loadDataSources() {
        Connection conn = null;
        try {
            if (StringUtils.hasText(driverClassName)) {
                Class.forName(driverClassName);
                log.info("成功加载数据库驱动程序");
            }
            String publicKey = System.getProperty("druid.config.decrypt.key");
            String decryptPassword = F.decrypt(F.getPublicKey(publicKey),password);
            conn = DriverManager.getConnection(url, username, decryptPassword);
            log.info("成功获取master数据库连接");
            List<SysTenantDb> list =  SysTenantDbUtil.getAllSysTenantDb(conn);
            Map<String, DataSourceProperty> dataSourcePropertiesMap = new ConcurrentHashMap<>();
            for(SysTenantDb sysTenantDb:list){
                DataSourceProperty dataSourceProperty = dataSourcePropertyConverter.sysTenantDb2DataSourceProperty(sysTenantDb);
                dataSourcePropertiesMap.put(sysTenantDb.getTenantId(),dataSourceProperty);
            }
            return createDataSourceMap(dataSourcePropertiesMap);
        } catch (Exception e) {
            log.error("init datasource error",e);
            throw new RuntimeException("init datasource error");
        } finally {
            JdbcUtils.closeConnection(conn);
        }
    }
}
