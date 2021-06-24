package com.fencer.entity;

import lombok.Data;

/**
 * @日期: 2021/6/2
 * @作者: dd
 * @描述: 物联网入库控制配置信息
 */
@Data
public class TableInputConfig {

    /**
     * 主键
     */
    private String id;

    /**
     * 设备编码
     */
    private String subdevCode;

    /**
     * 设备编码字段名
     */
    private String subdevCodeField;

    /**
     * 设备名称
     */
    private String subdevName;

    /**
     * 设备名称字段名
     */
    private String subdevNameField;

    /**
     * 推送时间字段名
     */
    private String pustTimeField;

    /**
     * 数据库表名
     */
    private String tableName;

    /**
     * 入库类型
     */
    private String inputType;

    /**
     * 厂家
     */
    private String manufactor;

    /**
     * 所属工程
     */
    private String belongProject;

    /**
     * 管理员
     */
    private String adminName;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 备注
     */
    private String remark;

}
