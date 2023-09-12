package com.yyl.dynamicds;

import org.springframework.jdbc.support.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yang.yonglian
 * @version 1.0.0
 * @Description TODO
 * @createTime 2023/6/3 10:37
 */
public class SysTenantDbUtil {
    public static List<SysTenantDb> getAllSysTenantDb(Connection conn){
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        List<SysTenantDb> list = new ArrayList<>();
        try {
            stmt = conn.prepareStatement("select tenant_id,db_host,db_port,db_database,db_user,db_password from sys_tenant_db where status=1 ");
            resultSet = stmt.executeQuery();
            while (resultSet.next()){
                SysTenantDb sysTenantDb = new SysTenantDb();
                sysTenantDb.setTenantId(resultSet.getString(1));
                sysTenantDb.setDbHost(resultSet.getString(2));
                sysTenantDb.setDbPort(resultSet.getString(3));
                sysTenantDb.setDbDatabase(resultSet.getString(4));
                sysTenantDb.setDbUser(resultSet.getString(5));
                sysTenantDb.setDbPassword(resultSet.getString(6));
                list.add(sysTenantDb);
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException("query getAllSysTenantDb error",e);
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(stmt);
        }
    }
}
