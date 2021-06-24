package com.fencer.controller;

import com.alibaba.fastjson.JSON;
import com.fencer.entity.TableInputConfig;
import com.fencer.entity.TableInputConfigParam;
import com.fencer.service.TableInputConfigService;
import com.fencer.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @日期: 2021/6/2
 * @作者: dd
 * @描述: 设备入库配置-控制层
 */
@Slf4j
@RestController
@RequestMapping(value = "/ec")
public class TableInputConfigController {

    @Resource
    private TableInputConfigService configService;

    @PostMapping(value = "/list")
    public ResponseResult list(@RequestBody TableInputConfigParam param, HttpServletRequest request) {

        String subdevCode = request.getParameter("subdevCode");
        String manufactor = request.getParameter("manufactor");
        String belongProject = request.getParameter("belongProject");
        String adminName = request.getParameter("adminName");

        param.setSubdevCode(subdevCode);
        param.setManufactor(manufactor);
        param.setBelongProject(belongProject);
        param.setAdminName(adminName);

        log.info("\n\t设备入库，控制层，请求参数：{}", JSON.toJSONString(param));

        List<TableInputConfig> list = configService.getList(param);

        int count = configService.count();

        return ResponseResult.success(list, count);

    }

    @PostMapping(value = "/add")
    public ResponseResult add(@RequestBody TableInputConfig param) {

        int i = configService.addRow(param);

        if (i > 0) {

            return ResponseResult.success("操作成功");

        }

        return ResponseResult.success("操作失败");

    }

    @GetMapping(value = "/info")
    public ResponseResult info(String id) {

        TableInputConfig info = configService.getInfo(id);

        return ResponseResult.success(info);

    }

    @PostMapping(value = "/update")
    public ResponseResult update(@RequestBody TableInputConfig param) {

        int i = configService.updateRow(param);

        if (i > 0) {

            return ResponseResult.success("操作成功");

        }

        return ResponseResult.success("操作失败");

    }

    @PostMapping(value = "/del")
    public ResponseResult del(@RequestParam(name = "ids[]") List<String> ids) {

        int i = configService.deleteRow(ids);

        if (i > 0) {

            return ResponseResult.success("操作成功");

        }

        return ResponseResult.success("操作失败");


    }


}
