package com.yyl.dynamicds;

import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.yyl.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

/**
 * @author yang.yonglian
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022/4/14 16:43
 */
@Slf4j
public class DynamicRoutingDataSource extends com.baomidou.dynamic.datasource.DynamicRoutingDataSource {

    private String master;

    @Autowired
    private DefaultDataSourceCreator defaultDataSourceCreator;
    @Autowired
    private DataSourcePropertyConverter dataSourcePropertyConverter;
    @Override
    public DataSource getDataSource(String ds) {
        Map<String, DataSource> dataSourceMap =  getDataSources();
        if (!StringUtils.hasLength(ds)) {
            return getMasterDatasourceSource();
        } else if (dataSourceMap.containsKey(ds)) {
            log.debug("dynamic-datasource switch to the datasource named [{}]", ds);
            return dataSourceMap.get(ds);
        }else{
            DataSource masterDataSource = getMasterDatasourceSource();
            DataSource dataSource = dataSourceMap.computeIfAbsent(ds,key->{
                Connection connection = null;
                PreparedStatement ps = null;
                ResultSet resultSet = null;
                try{
                    connection = masterDataSource.getConnection();
                    ps = connection.prepareStatement("select tenant_id,db_host,db_port,db_database,db_user,db_password from sys_tenant_db where status=1  and tenant_id=?");
                    ps.setString(1,ds);
                    resultSet = ps.executeQuery();
                    DataSource newDataSource = null;
                    while (resultSet.next()){
                        if(newDataSource!=null){
                            throw new BusinessException("存在多个企业编码相同的企业");
                        }
                        SysTenantDb sysTenantDb = new SysTenantDb();
                        sysTenantDb.setTenantId(resultSet.getString(1));
                        sysTenantDb.setDbHost(resultSet.getString(2));
                        sysTenantDb.setDbPort(resultSet.getString(3));
                        sysTenantDb.setDbDatabase(resultSet.getString(4));
                        sysTenantDb.setDbUser(resultSet.getString(5));
                        sysTenantDb.setDbPassword(resultSet.getString(6));
                        DataSourceProperty dataSourceProperty = dataSourcePropertyConverter.sysTenantDb2DataSourceProperty(sysTenantDb);
                        newDataSource = defaultDataSourceCreator.createDataSource(dataSourceProperty);
                    }
                    if(newDataSource==null){
                        throw new BusinessException("企业不存在");
                    }
                    return newDataSource;
                }catch (BusinessException e){
                    throw e;
                }catch (Exception e){
                    log.error("获取企业数据源失败",e);
                    throw new BusinessException("获取企业失败，请重试，请联系管理员");
                }finally {
                    JdbcUtils.closeResultSet(resultSet);
                    JdbcUtils.closeStatement(ps);
                    JdbcUtils.closeConnection(connection);
                }
            });
            return dataSource;
        }
    }

    public DataSource getMasterDatasourceSource(){
        return getDataSources().get(master);
    }


    public void setMaster(String master) {
        this.master = master;
    }
}
