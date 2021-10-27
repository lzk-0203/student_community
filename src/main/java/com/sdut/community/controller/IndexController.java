package com.sdut.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 24699
 */
@Controller
public class IndexController {
    @GetMapping("/")  // 根目录
    public String toIndex() {
        return "index";
    }
}
