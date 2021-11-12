package com.sdut.community.controller;

import com.sdut.community.dto.PaginationDTO;
import com.sdut.community.mapper.UserMapper;
import com.sdut.community.model.User;
import com.sdut.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 24699
 */
@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    /**
     * 1.去浏览器获取cookie
     * 2.找 key 为 token 的cookie
     * 3.利用本地 token 值去数据库匹配
     * 4.如果有改用户则加入 Session, 在index.html显示
     * @param request
     * @return index.html
     */
    @GetMapping("/")  // 根目录
    public String toIndex(HttpServletRequest request,
                          Model model,
                          @RequestParam(name="page", defaultValue = "1")Integer page,
                          @RequestParam(name="size", defaultValue = "5")Integer size) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        // 带有创建者user信息的传输类
        PaginationDTO pagination = questionService.list(page, size);
        model.addAttribute("pagination", pagination);
        return "index";
    }
}
