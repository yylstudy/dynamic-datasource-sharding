package com.yyl.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author yang.yonglian
 * @version 1.0.0
 * @Description TODO
 * @createTime 2023/9/11 17:53
 */
@Data
public class TraceLabel {

    /**
     * 标签编码
     */
    private String code;
    /**
     * 标签类型id
     */
    private String labelTypeId;
    /**
     * 标签图片地址
     */
    private String labelImg;
    /**
     * 订单id
     */
    private String orderId;
    /**
     * 标签采购单id
     */
    private String orderSupplierId;
    /**
     * 上链状态（该字段弃用，查看标签上链状态请转移至trace_label_chain表）
     */
    private String chainStatus;
    /**
     * 租户id
     */
    private String tenandId;
    /**
     * sku信息
     */
    private String skuCode;
    /**
     * 批次id
     */
    private String batchId;
    /**
     * 商品id
     */
    private String productId;

    private Integer isTest;

    /**
     * 所属商户
     */
    private String userMerchant;
    /**
     * milvus id
     */
    private String milvusId;
    /**
     * 码类型
     */
    private String codeType;

    private String uuid;

    private Date createTime;

    private String creatorId;
    private String creator;
}
