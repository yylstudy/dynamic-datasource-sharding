package com.yyl.sharding;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.yyl.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author yang.yonglian
 * @version 1.0.0
 * @Description 复合分片算法
 * @createTime 2022/12/8 9:42
 */
@Slf4j
public class TraceLabelComplexKeysShardingAlgorithm implements ComplexKeysShardingAlgorithm {
    @Override
    public Collection<String> doSharding(Collection availableTargetNames, ComplexKeysShardingValue shardingValue) {
        Map<String, List<Object>> map = shardingValue.getColumnNameAndShardingValuesMap();
        List<Object> tenandIds = map.get("tenand_id");
        List<Object> createTimes = map.get("create_time");
        if(CollectionUtils.isEmpty(tenandIds)||
                CollectionUtils.isEmpty(createTimes)||tenandIds.size()!=createTimes.size()){
            throw new BusinessException("tenand_id、create_time数据缺失，请检查");
        }
        List<String> tableNames = new ArrayList<>();
        for(int i=0;i<tenandIds.size();i++){
            String tenandId = (String)tenandIds.get(i);
            Date createTime = (Date)createTimes.get(i);
            String year = DateUtil.format(createTime, DatePattern.NORM_YEAR_PATTERN);
            String tableName = String.format("%s_%s_%s",shardingValue.getLogicTableName(),tenandId,year);
            tableNames.add(tableName);
        }
        return tableNames;
    }

    @Override
    public Properties getProps() {
        return null;
    }

    @Override
    public void init(Properties props) {

    }
}
