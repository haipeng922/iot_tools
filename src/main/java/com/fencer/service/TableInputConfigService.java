package com.fencer.service;

import com.fencer.entity.TableInputConfig;
import com.fencer.entity.TableInputConfigParam;

import java.util.List;

/**
 * @日期: 2021/6/2
 * @作者: dd
 * @描述: 设备入库配置
 */
public interface TableInputConfigService {

    /**
     * 获取入库控制配置列表
     * @param param
     * @return
     */
    List<TableInputConfig> getList(TableInputConfigParam param);

    /**
     * 获取数量，分页
     * @return
     */
    int count();

    /**
     * 新增
     * @param param
     * @return
     */
    int addRow(TableInputConfig param);

    /**
     * 详情
     * @param id
     * @return
     */
    TableInputConfig getInfo(String id);

    /**
     * 更新
     * @param param
     * @return
     */
    int updateRow(TableInputConfig param);

    /**
     * 删除
     * @param ids
     * @return
     */
    int deleteRow(List<String> ids);

}
