package com.fencer.entity;

import lombok.Data;

/**
 * @日期: 2021/6/2
 * @作者: dd
 * @描述:
 */
@Data
public class TableInputConfigParam extends PageParam {

    /**
     * 设备编码
     */
    private String subdevCode;

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

}
