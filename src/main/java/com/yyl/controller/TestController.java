package com.yyl.controller;

import com.yyl.dynamicds.DynamicDatasourceUtil;
import com.yyl.entity.TraceLabel;
import com.yyl.mapper.TraceLabelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author yang.yonglian
 * @version 1.0.0
 * @Description TODO
 * @createTime 2023/9/11 15:49
 */
@RestController
public class TestController {
    @Autowired
    private TraceLabelMapper traceLabelMapper;
    @RequestMapping("test1")
    public String test1(){
        DynamicDatasourceUtil.doExecute("master",()->{
            List<TraceLabel> entityList = new ArrayList<>();
            TraceLabel traceLabel = new TraceLabel();
            traceLabel.setUuid(UUID.randomUUID().toString().replace("-",""));
            traceLabel.setCode(UUID.randomUUID().toString().replace("-",""));
            traceLabel.setTenandId("1001");
            traceLabel.setCreateTime(new Date());
            entityList.add(traceLabel);
            traceLabel = new TraceLabel();
            traceLabel.setUuid(UUID.randomUUID().toString().replace("-",""));
            traceLabel.setCode(UUID.randomUUID().toString().replace("-",""));
            traceLabel.setTenandId("1002");
            traceLabel.setCreateTime(new Date());
            entityList.add(traceLabel);
            traceLabelMapper.insertAll(entityList);

            return null;
        });
        return "success";
    }
}
