package com.sdut.community.controller;

import com.sdut.community.dto.AccessTokenDTO;
import com.sdut.community.dto.GithubUser;
import com.sdut.community.model.User;
import com.sdut.community.provider.GithubProvider;
import com.sdut.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author 24699
 */
@Controller
public class AuthorizeController {
    @Value("${github.client.id}")
    private String clientID;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private UserService userService;

    /**
     * 1.填入Client的个人信息
     * 2.获取accessToken
     * 3.获取User信息
     * 4.若获取信息不为空，写入数据库和 Cookie
     * @param code
     * @param state
     * @param response
     * @return
     */
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientID);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubuser = githubProvider.getUser(accessToken);

        // 数据库中可能存在空值，为获取信息不写入cookie
        if (githubuser != null && githubuser.getId() != null) {
            // 生成Token
            String token = UUID.randomUUID().toString();
            // 写入数据库
            User user = new User();
            user.setToken(token);
            user.setName(githubuser.getName());
            user.setAccountId(String.valueOf(githubuser.getId()));
            user.setAvatarUrl(githubuser.getAvatarUrl());
            userService.createOrUpdate(user);
            // 写入cookie
            Cookie cookie = new Cookie("token", token);
            cookie.setMaxAge(60*60*24*30);
            response.addCookie(cookie);
        } else {
            // 登录失败，重新登录
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        // 去除session cookie 有效期设置为0
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";

    }
}
