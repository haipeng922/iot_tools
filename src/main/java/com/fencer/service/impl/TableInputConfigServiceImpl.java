package com.fencer.service.impl;

import com.alibaba.fastjson.JSON;
import com.fencer.entity.TableInputConfig;
import com.fencer.entity.TableInputConfigParam;
import com.fencer.mapper.CommonMapper;
import com.fencer.service.TableInputConfigService;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * @日期: 2021/6/2
 * @作者: dd
 * @描述: 设备入库配置
 */
@Slf4j
@Service
public class TableInputConfigServiceImpl implements TableInputConfigService {

    @Resource
    private CommonMapper mapper;

    /**
     * 获取入库控制配置列表
     *
     * @param param
     * @return
     */
    @Override
    public List<TableInputConfig> getList(TableInputConfigParam param) {

        try {

            PageHelper.startPage(param.getPage(), param.getLimit());
            List<TableInputConfig> list = mapper.getList(param);
            return list;

        } catch(Exception e) {

            e.printStackTrace();

        }

        return null;
    }

    /**
     * 获取数量，分页
     *
     * @return
     */
    @Override
    public int count() {

        try {

            int count = mapper.count();
            return count;

        } catch(Exception e) {

            e.printStackTrace();

        }

        return 0;
    }

    /**
     * 新增
     *
     * @param param
     * @return
     */
    @Override
    public int addRow(TableInputConfig param) {

        try {

            log.info("\n\t入库配置，新增-请求报文：{}", JSON.toJSONString(param));

            param.setId(UUID.randomUUID().toString());
            int i = mapper.addRow(param);
            return i;

        } catch(Exception e) {

            e.printStackTrace();

        }

        return 0;
    }

    /**
     * 详情
     *
     * @param id
     * @return
     */
    @Override
    public TableInputConfig getInfo(String id) {

        try {

            log.info("\n\t入库配置，详情-请求报文：{}", id);

            TableInputConfig info = mapper.getInfo(id);

            log.info("\n\t入库配置，详情-返回报文：{}", JSON.toJSONString(info));

            return info;

        } catch(Exception e) {

            e.printStackTrace();

        }

        return null;
    }

    /**
     * 更新
     *
     * @param param
     * @return
     */
    @Override
    public int updateRow(TableInputConfig param) {

        try {

            log.info("\n\t入库配置，更新-请求报文：{}", JSON.toJSONString(param));

            int i = mapper.updateRow(param);
            return i;

        } catch(Exception e) {

            e.printStackTrace();

        }

        return 0;
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @Override
    public int deleteRow(List<String> ids) {

        StringBuffer sb = new StringBuffer("'");

        String id = "";

        for(int i = 0; i < ids.size();i++){
            id = sb.append(ids.get(i)).append("','").toString();
        }

        id = id.substring(0,id.length() - 2);

        log.info("\n\t入库配置，删除-请求报文：{}", id);

        try {

            int i = mapper.deleteRow(id);
            return i;

        } catch(Exception e) {

            e.printStackTrace();

        }

        return 0;
    }




}
