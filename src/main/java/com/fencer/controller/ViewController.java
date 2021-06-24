package com.fencer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Date:           2020/3/3
 * @Author:         dengpeng
 * @Description:    前端跳转控制层
 */
@Controller
public class ViewController {

    @GetMapping("/")
    public String redirectIndex() {
        return "redirect:/home";
    }

    @GetMapping("index")
    public String index() {
        return "index";
    }

    @GetMapping("home")
    public String home() {
        return "home";
    }

    @GetMapping("inputConfig")
    public String inputConfig() {
        return "inputConfig";
    }

}
