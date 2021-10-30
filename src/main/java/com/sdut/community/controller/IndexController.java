package com.sdut.community.controller;

import com.sdut.community.mapper.UserMapper;
import com.sdut.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 24699
 */
@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    /**
     * 1.去浏览器获取cookie
     * 2.找 key 为 token 的cookie
     * 3.利用本地 token 值去数据库匹配
     * 4.如果有改用户则加入 Session, 在index.html显示
     * @param request
     * @return index.html
     */
    @GetMapping("/")  // 根目录
    public String toIndex(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
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
        return "index";
    }
}
