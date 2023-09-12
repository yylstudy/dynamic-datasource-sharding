package com.yyl.dynamicds;

import lombok.Data;

/**
 * @author yang.yonglian
 * @version 1.0.0
 * @Description TODO
 * @createTime 2023/6/3 9:59
 */
@Data
public class SysTenantDb {
    /**
     * 租户ID
     */
    private String tenantId;
    /**
     * 数据库IP
     */
    private String dbHost;
    /**
     * 数据库端口
     */
    private String dbPort;
    /**
     * database
     */
    private String dbDatabase;
    /**
     * 数据库用户
     */
    private String dbUser;
    /**
     * 数据库密码
     */
    private String dbPassword;
}
