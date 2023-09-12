package com.yyl.mapper;

import com.yyl.entity.TraceLabel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 标签码表
 *
 * @Author dingsh
 * @Version V1.1.0
 * @Date 2023-06-06 20:06:01
 */
public interface TraceLabelMapper  {

    int insertAll(@Param("entityList") List<TraceLabel> entityList);
}
