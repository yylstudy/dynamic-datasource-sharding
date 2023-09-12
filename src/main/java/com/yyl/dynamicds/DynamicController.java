package com.yyl.dynamicds;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @author yang.yonglian
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022/4/20 16:53
 */
@RestController
@Slf4j
public class DynamicController {
    @Autowired
    private DynamicRoutingDataSource dynamicRoutingDataSource;

    @RequestMapping("getDynamicDatasourceByTenantId")
    public String getDynamicDatasource(String tenantId){
        if(!StringUtils.hasText(tenantId)){
            log.info("datasource:{}",tenantId, JSONObject.toJSONString(dynamicRoutingDataSource.getDataSources()));
        }else{
            DataSource dataSource = dynamicRoutingDataSource.getDataSource(tenantId);
            try(Connection connection = dataSource.getConnection()){
                log.info("tenantId:{} ,datasource:{}",tenantId, JSONObject.toJSONString(dataSource));
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        return "success";
    }
}
