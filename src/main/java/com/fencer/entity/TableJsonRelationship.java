package com.fencer.entity;

import lombok.Data;

/**
 * @日期: 2021/5/26
 * @作者: dd
 * @描述:
 */
@Data
public class TableJsonRelationship {

    /**
     * id
     */
    private String id;

    /**
     * 设备编号
     */
    private String subdevCode;

    /**
     * 设备编号数据库名称
     */
    private String subdevCodeName;

    /**
     * 数据库表名
     */
    private String tableName;

    /**
     * 数据库字段名称
     */
    private String fieldName;

    /**
     * 数据库字段类型
     */
    private String fieldType;

    /**
     * json键的名称
     */
    private String jsonName;

    /**
     * 推送时间数据库名称
     */
    private String pushTimeName;

    /**
     * 备注
     */
    private String remark;

}
