package com.yyl.dynamicds;//package com.linkcircle.act.dynamicds;//package com.linkcircle.core.common.dynamicds;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
///**
// * @author yang.yonglian
// * @version 1.0.0
// * @Description TODO
// * @createTime 2022/4/14 18:08
// */
//@Component
//@Slf4j
//@EnableScheduling
//public class DyncmicDatasourceMonitor {
//    @Autowired
//    private DynamicRoutingDataSource dynamicRoutingDataSource;
//
//    @Scheduled(cron = "${clearUnUseDataSource.cron}")
//    public void clearUnUseDataSource(){
//        try(Connection connection = dynamicRoutingDataSource.getConnection()){
//            List<SysTenantDb> sysTenantDbList = SysTenantDbUtil.getAllSysTenantDb(connection);
//            List<String> list = sysTenantDbList.stream().map(SysTenantDb::getTenantId).collect(Collectors.toList());
//            Map<String, DataSource> dataSourceMap = dynamicRoutingDataSource.getDataSources();
//            List<String> removeDatasource = dataSourceMap.keySet().stream()
//                    .filter(key->!"master".equals(key)&&!list.contains(key)).collect(Collectors.toList());
//            log.info("removeDatasource:{}",removeDatasource);
//            removeDatasource.stream().forEach(dynamicRoutingDataSource::removeDataSource);
//        }catch (Exception e){
//            throw new RuntimeException(e);
//        }
//    }
//}
