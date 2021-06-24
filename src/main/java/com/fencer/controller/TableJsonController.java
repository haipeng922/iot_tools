package com.fencer.controller;

import com.alibaba.fastjson.JSON;
import com.fencer.entity.TableJsonRelationship;
import com.fencer.service.TableJsonService;
import com.fencer.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @日期: 2021/5/26
 * @作者: dd
 * @描述:
 */
@Slf4j
@RestController
@RequestMapping(value = "/tableJson")
public class TableJsonController {

    @Resource
    private TableJsonService tableJsonService;

    @GetMapping(value = "/list")
    public ResponseResult list(int page, int limit, HttpServletRequest request) {

        String subdevCode = request.getParameter("subdevCode");

        log.info("\n\t列表，请求报文:subdevCode={}",subdevCode);

        List<TableJsonRelationship> list = tableJsonService.list(page,limit,subdevCode);

        int count = tableJsonService.getCount(null);

        return ResponseResult.success(list,count);

    }

    /**
     * 新增
     * @param info
     * @return
     */
    @PostMapping(value = "/add")
    public ResponseResult add(@RequestBody TableJsonRelationship info) {

        log.info("\n\t数据库json对照表,新增，请求报文：{}",JSON.toJSONString(info));

        int i = tableJsonService.add(info);

        if (i == 1){
            return ResponseResult.success("操作成功");
        }

        return ResponseResult.error("操作失败");
    }

    /**
     * 详情
     * @param id
     * @return
     */
    @GetMapping(value = "/info")
    public ResponseResult info(String id) {

        log.info("\n\t详情接口，请求报文：id={}",id);

        TableJsonRelationship info = tableJsonService.info(id);

        log.info("\n\t详情接口，返回报文：info：{}",JSON.toJSONString(info));

        return ResponseResult.success(info);

    }

    /**
     * 编辑
     * @param info
     * @return
     */
    @PostMapping(value = "/update")
    public ResponseResult update(@RequestBody TableJsonRelationship info) {

        log.info("\n\t数据库json对照表,编辑，请求报文：{}",JSON.toJSONString(info));

        int i = tableJsonService.update(info);

        if (i == 1){
            return ResponseResult.success("操作成功");
        }

        return ResponseResult.error("操作失败");
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @PostMapping(value = "/del")
    public ResponseResult del(@RequestParam(name = "ids[]") List<String> ids) {

        int del = tableJsonService.del(ids);

        if (del >= 1){
            return ResponseResult.success("操作成功");
        }

        return ResponseResult.error("操作失败");


    }

}
