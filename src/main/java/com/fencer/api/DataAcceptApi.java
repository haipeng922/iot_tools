package com.fencer.api;

import com.fencer.service.DataAcceptService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Date:           2020/12/10
 * @Author:         dengpeng
 * @Description:    物联网数据接受
 */
@Slf4j
@RestController
@RequestMapping(value = "/equipment")
public class DataAcceptApi {

    @Resource
    private DataAcceptService dataAcceptService;

    /**
     * 接受来自物联网的流量计数据
     * @param data
     */
    @RequestMapping(value = "/dataAccept",method = RequestMethod.POST)
    public void dataAccept(@RequestBody String data) {

        if (StringUtils.isNotBlank(data)) {

            dataAcceptService.dataAccept(data);

        }

    }


}
