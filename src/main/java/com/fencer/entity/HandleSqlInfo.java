package com.fencer.entity;

import lombok.Data;

/**
 * @日期: 2021/5/26
 * @作者: dd
 * @描述:
 */
@Data
public class HandleSqlInfo {

    String sql;

    int state = 0;

    String sqlHandleType;

}
