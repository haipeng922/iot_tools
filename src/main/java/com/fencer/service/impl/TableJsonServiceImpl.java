package com.fencer.service.impl;

import com.fencer.entity.TableJsonRelationship;
import com.fencer.mapper.CommonMapper;
import com.fencer.service.TableJsonService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * @日期: 2021/5/26
 * @作者: dd
 * @描述:
 */
@Service
public class TableJsonServiceImpl implements TableJsonService {

    @Resource
    private CommonMapper commonMapper;

    /**
     * 获取对照表 列表
     * @return
     */
    @Override
    public List<TableJsonRelationship> list(int page,int limit,String subdevCode) {

        try {

            PageHelper.startPage(page,limit);
            List<TableJsonRelationship> list = commonMapper.list(subdevCode);
            return list;

        } catch(Exception e) {

            e.printStackTrace();

        }

        return null;
    }

    @Override
    public int getCount(String subdevCode) {

        try {
            int count = commonMapper.getCount(subdevCode);
            return count;
        } catch(Exception e) {

            e.printStackTrace();

        }

        return 0;
    }

    /**
     * 新增
     *
     * @param info
     * @return
     */
    @Override
    public int add(TableJsonRelationship info) {

        try {
            info.setId(UUID.randomUUID().toString());
            return commonMapper.add(info);

        } catch(Exception e) {

            e.printStackTrace();

        }

        return 0;
    }

    /**
     * 根据ID查询详情信息
     *
     * @param id
     * @return
     */
    @Override
    public TableJsonRelationship info(String id) {

        try {

            TableJsonRelationship info = commonMapper.info(id);
            return info;

        } catch(Exception e) {

            e.printStackTrace();

        }

        return null;
    }

    /**
     * 编辑
     *
     * @param info
     * @return
     */
    @Override
    public int update(TableJsonRelationship info) {

        try {

            int update = commonMapper.update(info);
            return update;

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
    public int del(List<String> ids) {

        StringBuffer sb = new StringBuffer("'");

        String id = "";

        for(int i = 0; i < ids.size();i++){
            id = sb.append(ids.get(i)).append("','").toString();
        }

        id = id.substring(0,id.length() - 2);

        try {

            int del = commonMapper.del(id);
            return del;

        } catch(Exception e) {

            e.printStackTrace();

        }

        return 0;
    }
}
