package com.sdut.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 24699
 */
@Controller
public class IndexController {
    @GetMapping("/")  // 根目录
    public String index() {
        return "index";
    }
}
