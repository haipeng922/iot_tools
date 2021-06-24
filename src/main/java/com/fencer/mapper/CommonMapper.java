package com.fencer.mapper;

import com.fencer.entity.TableInputConfig;
import com.fencer.entity.TableInputConfigParam;
import com.fencer.entity.TableJsonRelationship;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @日期: 2021/5/26
 * @作者: dd
 * @描述: 公共接口mapper层
 */
@Mapper
public interface CommonMapper {

    /**
     * 根据设备编号获取list集合
     * @return
     */
    List<TableJsonRelationship> list(@Param(value = "subdevCode") String subdevCode);

    /**
     * 拼接sql的执行
     * @param sql
     */
    @Update(value = "${sql}")
    void sqlHandle(@Param(value = "sql") String sql);

    /**
     * 获取数据量根据设备id
     * @param sql
     * @return
     */
    @Select(value = "${sql}")
    int sqlGetCount(@Param(value = "sql") String sql);

    /**
     * 根据设备code删除
     * @param sql
     * @return
     */
    @Delete(value = "${sql}")
    int sqlDelete(@Param(value = "sql") String sql);

    /**
     * 获取数量
     * @param subdevCode
     * @return
     */
    int getCount(@Param(value = "subdevCode") String subdevCode);

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
    TableJsonRelationship info(@Param(value = "id") String id);

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
    int del(@Param(value = "ids") String ids);

    /**
     * 获取入库控制配置列表
     * @param param
     * @return
     */
    List<TableInputConfig> getList(TableInputConfigParam param);

    /**
     * 数量
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
    TableInputConfig getInfo(@Param(value = "id") String id);

    /**
     * 根据设备编号获取当前的入库模式
     * @param subdevCode
     * @return
     */
    TableInputConfig getInfoBySubdevCode(@Param(value = "subdevCode") String subdevCode);

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
    int deleteRow(@Param(value = "ids") String ids);

}
