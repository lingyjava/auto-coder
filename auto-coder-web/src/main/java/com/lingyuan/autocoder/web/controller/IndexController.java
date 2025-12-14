package com.lingyuan.autocoder.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 页面控制器
 * 
 * @author LingYuan
 */
@Controller
public class IndexController {

    /**
     * 首页
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * 代码生成页面
     */
    @GetMapping("/generator")
    public String generator() {
        return "index";
    }

    /**
     * 关于页面
     */
    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
