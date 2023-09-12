package com.yyl.dynamicds;

import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author yang.yonglian
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022/4/15 13:39
 */
@Component
public class DataSourcePropertyConverter {
    @Value("${mysql.url.format}")
    private String urlFormat;
    @Autowired
    private DynamicDataSourceProperties properties;

    public  DataSourceProperty sysTenantDb2DataSourceProperty(SysTenantDb sysTenantDb){
        try{
            DataSourceProperty dataSourceProperty = new DataSourceProperty();
            String url = String.format(urlFormat,sysTenantDb.getDbHost(),sysTenantDb.getDbPort(), sysTenantDb.getDbDatabase());
            dataSourceProperty.setUrl(url);
            dataSourceProperty.setPoolName("act-"+sysTenantDb.getTenantId());
            DataSourceProperty masterDataSourceProperty = properties.getDatasource().get(properties.getPrimary());
            dataSourceProperty.setHikari(masterDataSourceProperty.getHikari());
            dataSourceProperty.setType(masterDataSourceProperty.getType());
            dataSourceProperty.setUsername(sysTenantDb.getDbUser());
            dataSourceProperty.setPassword(sysTenantDb.getDbPassword());
            return dataSourceProperty;
        }catch (Exception e){
            throw new RuntimeException("初始化企业失败");
        }

    }
}
