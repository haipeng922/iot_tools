package com.fencer.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fencer.entity.HandleSqlInfo;
import com.fencer.entity.TableInputConfig;
import com.fencer.entity.TableJsonRelationship;
import com.fencer.mapper.CommonMapper;
import com.fencer.service.DataAcceptService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @日期: 2021/5/8
 * @作者: dd
 * @描述: 物联网，数据处理，实现类
 */
@Slf4j
@Service
public class DataAcceptServiceImpl implements DataAcceptService {

    @Resource
    private CommonMapper commonMapper;

    /**
     * 数据接受
     * @param data
     */
    @Override
    public void dataAccept(String data) {

        log.info("\n\t锋士物联网平台，接受的数据：{}",data);

        try {

            //  将请求参数 转化成json格式
            JSONArray array = JSONArray.parseArray(data);

            //  初始化可执行sql集合
            List<HandleSqlInfo> handleSqlList = new ArrayList<>();

            array.forEach(info -> {

                JSONObject jsonObject = (JSONObject) info;

                //  设备ID
                String subdevCode = jsonObject.getString("subdev_code");

                //  todo 根据设备号去查看入库模式，直接入库，更新入库
                TableInputConfig inputConfig = commonMapper.getInfoBySubdevCode(subdevCode);

                //  初始化可执行sql的对象
                HandleSqlInfo handleSqlInfo = new HandleSqlInfo();

                if (ObjectUtil.isNotEmpty(inputConfig)) {

                    switch (inputConfig.getInputType()) {

                        case "Update_Insert":
                            //  更新入库
                            handleSqlInfo = update(jsonObject, inputConfig);
                            handleSqlInfo.setSqlHandleType("update");
                            break;
                        case "Insert":
                            //  直接入库
                            handleSqlInfo = direct(jsonObject, inputConfig);
                            handleSqlInfo.setSqlHandleType("insert");
                            break;

                    }

                } else {

                   log.error("\n\t请配置设备入库模式");

                }

                handleSqlList.add(handleSqlInfo);

            });

            log.info("\n\t执行的sql集合为：{}",JSONObject.toJSONString(handleSqlList));

            if (handleSqlList.size() > 0) {
                execute(handleSqlList);
            }

        } catch(Exception e) {

            e.printStackTrace();

        }

    }

    /**
     * 直接入库
     * @param jsonObject
     * @param inputConfig
     * @return
     */
    public HandleSqlInfo direct(JSONObject jsonObject,TableInputConfig inputConfig) {

        //  初始化可执行sql对象
        HandleSqlInfo handleSqlInfo = new HandleSqlInfo();

        //  推送时间
        String pushTime = jsonObject.getString("push_time");

        List<TableJsonRelationship> list = commonMapper.list(inputConfig.getSubdevCode());

        if (list.size() > 0) {

            StringBuffer sqlHead = new StringBuffer();
            StringBuffer sqlTail = new StringBuffer();
            StringBuffer sql = new StringBuffer();

            sqlHead.append("INSERT INTO ");
            sqlHead.append(inputConfig.getTableName());
            sqlHead.append(" (");
            sqlHead.append("ID,");
            sqlHead.append(inputConfig.getSubdevCodeField());
            sqlHead.append(",");
            sqlHead.append(inputConfig.getPustTimeField());
            sqlHead.append(",");
            sqlHead.append(inputConfig.getSubdevNameField());
            sqlHead.append(",");

            sqlTail.append(")");
            sqlTail.append(" VALUES(");
            sqlTail.append(" '");
            sqlTail.append(UUID.randomUUID().toString());
            sqlTail.append("','");
            sqlTail.append(inputConfig.getSubdevCode());
            sqlTail.append("','");
            sqlTail.append(pushTime);
            sqlTail.append("','");
            sqlTail.append(inputConfig.getSubdevName());
            sqlTail.append("',");

            JSONObject tmData = jsonObject.getJSONObject("tm_data");

            JSONObject fields = tmData.getJSONObject("fields");

            //  todo 物联网请求报文，循环拼接
            list.forEach(dto -> {

                String item = null;

                switch (dto.getFieldType()) {

                    case "String":
                        if (StringUtils.isNotBlank(fields.getString(dto.getJsonName()))) {

                            item = dto.getFieldName();
                            sqlHead.append(item);
                            sqlHead.append(",");

                            String strValue = fields.getString(dto.getJsonName());
                            sqlTail.append("'");
                            sqlTail.append(strValue);
                            sqlTail.append("',");

                            handleSqlInfo.setState(1);
                        }
                        break;
                    case "Double":
                        if (ObjectUtil.isNotEmpty(fields.getDouble(dto.getJsonName()))) {

                            item = dto.getFieldName();
                            sqlHead.append(item);
                            sqlHead.append(",");

                            double doubleValue = fields.getDouble(dto.getJsonName());
                            sqlTail.append(doubleValue);
                            sqlTail.append(",");

                            handleSqlInfo.setState(1);
                        }
                        break;
                }

            });

            String sqlHeads = StringUtils.substring(sqlHead.toString(),0,sqlHead.toString().length() -1);
            String sqlTails = StringUtils.substring(sqlTail.toString(),0,sqlTail.toString().length() -1);

            sql.append(sqlHeads);
            sql.append(sqlTails);
            sql.append(")");

            handleSqlInfo.setSql(sql.toString());

        }

        return handleSqlInfo;

    }

    /**
     * 更新入库
     * @param jsonObject
     * @param inputConfig
     * @return
     */
    public HandleSqlInfo update(JSONObject jsonObject,TableInputConfig inputConfig) {

        //  初始化可执行sql对象
        HandleSqlInfo handleSqlInfo = new HandleSqlInfo();

        //  推送时间
        String pushTime = jsonObject.getString("push_time");

        List<TableJsonRelationship> list = commonMapper.list(inputConfig.getSubdevCode());

        if (list.size() > 0) {

            String subdevCode = inputConfig.getSubdevCode();
            String tableName = inputConfig.getTableName();
            String code = inputConfig.getSubdevCodeField();
            String pushTimeName = inputConfig.getPustTimeField();

            // todo 通过表名和subdevCode 查询数据库有没有数据，有更新，无插入
            String sqlGetCount = "select count(id) from " + tableName + " where " + code + "='" + subdevCode + "'";

            int i = commonMapper.sqlGetCount(sqlGetCount);

            if (i == 0) {

                //  todo 插入 .. 开始拼接sql

                StringBuffer sqlHead = new StringBuffer();
                StringBuffer sqlTail = new StringBuffer();
                StringBuffer sql = new StringBuffer();

                sqlHead.append("INSERT INTO ");
                sqlHead.append(tableName);
                sqlHead.append(" (");
                sqlHead.append("ID,");
                sqlHead.append(code);
                sqlHead.append(",");
                sqlHead.append(pushTimeName);
                sqlHead.append(",");
                sqlHead.append(inputConfig.getSubdevNameField());
                sqlHead.append(",");

                sqlTail.append(")");
                sqlTail.append(" VALUES(");
                sqlTail.append(" '");
                sqlTail.append(UUID.randomUUID().toString());
                sqlTail.append("','");
                sqlTail.append(subdevCode);
                sqlTail.append("','");
                sqlTail.append(pushTime);
                sqlTail.append("','");
                sqlTail.append(inputConfig.getSubdevName());
                sqlTail.append("',");

                JSONObject tmData = jsonObject.getJSONObject("tm_data");

                JSONObject fields = tmData.getJSONObject("fields");

                //  todo 物联网请求报文，遍历拼接
                list.forEach(dto -> {

                    String item = null;

                    switch (dto.getFieldType()) {

                        case "String":
                            if (StringUtils.isNotBlank(fields.getString(dto.getJsonName()))) {

                                item = dto.getFieldName();
                                sqlHead.append(item);
                                sqlHead.append(",");

                                String strValue = fields.getString(dto.getJsonName());
                                sqlTail.append("'");
                                sqlTail.append(strValue);
                                sqlTail.append("',");

                                handleSqlInfo.setState(1);
                            }
                            break;
                        default :
                            if (ObjectUtil.isNotEmpty(fields.getDouble(dto.getJsonName()))) {

                                item = dto.getFieldName();
                                sqlHead.append(item);
                                sqlHead.append(",");

                                double doubleValue = fields.getDouble(dto.getJsonName());
                                sqlTail.append(doubleValue);
                                sqlTail.append(",");

                                handleSqlInfo.setState(1);
                            }
                            break;
                    }

                });

                String sqlHeads = StringUtils.substring(sqlHead.toString(),0,sqlHead.toString().length() -1);
                String sqlTails = StringUtils.substring(sqlTail.toString(),0,sqlTail.toString().length() -1);

                sql.append(sqlHeads);
                sql.append(sqlTails);
                sql.append(")");

                handleSqlInfo.setSql(sql.toString());

            } else {

                StringBuffer sqlHead = new StringBuffer();
                StringBuffer sqlTail = new StringBuffer();
                StringBuffer sql = new StringBuffer();

                sqlHead.append("UPDATE ");
                sqlHead.append(tableName);
                sqlHead.append(" set ");
                sqlHead.append(pushTimeName);
                sqlHead.append(" = '");
                sqlHead.append(pushTime);
                sqlHead.append("',");
                sqlHead.append(inputConfig.getSubdevNameField());
                sqlHead.append(" = '");
                sqlHead.append(inputConfig.getSubdevName());
                sqlHead.append("',");

                sqlTail.append(" where ");
                sqlTail.append(code);
                sqlTail.append("='");
                sqlTail.append(subdevCode);
                sqlTail.append("'");

                JSONObject tmData = jsonObject.getJSONObject("tm_data");

                JSONObject fields = tmData.getJSONObject("fields");

                //  todo 物联网，请求报文拼接处理
                list.forEach(dto -> {

                    String item = null;

                    switch (dto.getFieldType()) {

                        case "String":
                            if (StringUtils.isNotBlank(fields.getString(dto.getJsonName()))) {

                                item = dto.getFieldName();
                                String strValue = fields.getString(dto.getJsonName());

                                sqlHead.append(item);
                                sqlHead.append("='");
                                sqlHead.append(strValue);
                                sqlHead.append("',");

                                handleSqlInfo.setState(1);
                            }
                            break;
                        default :
                            if (ObjectUtil.isNotEmpty(fields.getDouble(dto.getJsonName()))) {

                                item = dto.getFieldName();
                                double doubleValue = fields.getDouble(dto.getJsonName());

                                sqlHead.append(item);
                                sqlHead.append("=");
                                sqlHead.append(doubleValue);
                                sqlHead.append(",");

                                handleSqlInfo.setState(1);
                            }
                            break;
                    }

                });

                String sqlHeads = StringUtils.substring(sqlHead.toString(),0,sqlHead.toString().length() -1);

                sql.append(sqlHeads);
                sql.append(sqlTail);

                handleSqlInfo.setSql(sql.toString());

            }

        }

        return handleSqlInfo;

    }

    /**
     * 删除 插入
     * @param jsonObject
     * @param inputConfig
     * @return
     */
    public HandleSqlInfo deleteInsert(JSONObject jsonObject,TableInputConfig inputConfig) {

        HandleSqlInfo info = new HandleSqlInfo();

        String subdevCode = inputConfig.getSubdevCode();

        try {

            String delSql = "delete from " + inputConfig.getTableName() + " where " + inputConfig.getSubdevCodeField() + " = '" + subdevCode + "'";

            //  执行删除
            commonMapper.sqlDelete(delSql);

            //  执行插入


        } catch(Exception e) {

            e.printStackTrace();

        }

        return info;

    }

    /**
     * @Description:    数据库处理
     * @Author:         dengpeng
     * @Date:           2021/5/26 15:54
     */
    public void execute(List<HandleSqlInfo> list) {

        try {

            List<HandleSqlInfo> lists = list.stream().filter(info -> info.getState() == 1).collect(Collectors.toList());

            log.info("\n\t【最终】执行的sql集合为：{}",JSONObject.toJSONString(lists));

            //  若是，更新，则仅执行最后一条
            if (lists.get(0).getSqlHandleType().equals("update")) {

                commonMapper.sqlHandle(lists.get(lists.size() - 1).getSql());

            } else {

                lists.forEach(info -> {
                    commonMapper.sqlHandle(info.getSql());
                });

            }

        } catch(Exception e) {

            e.printStackTrace();

        }

    }





}
