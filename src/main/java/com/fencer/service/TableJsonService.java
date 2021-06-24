package com.fencer.service;

import com.fencer.entity.TableJsonRelationship;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @日期: 2021/5/26
 * @作者: dd
 * @描述:
 */
public interface TableJsonService {

    /**
     * 获取对照表 列表
     * @return
     */
    List<TableJsonRelationship> list(int page,int limit,String subdevCode);

    /**
     * 获取列表数量
     * @param subdevCode
     * @return
     */
    int getCount(String subdevCode);

    /**
     * 新增
     * @param info
     * @return
     */
    int add(TableJsonRelationship info);

    /**
     * 根据ID查询详情信息
     * @param id
     * @return
     */
    TableJsonRelationship info(String id);

    /**
     * 编辑
     * @param info
     * @return
     */
    int update(TableJsonRelationship info);

    /**
     * 删除
     * @param ids
     * @return
     */
    int del(List<String> ids);

}
