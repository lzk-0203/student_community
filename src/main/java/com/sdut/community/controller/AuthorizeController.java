package com.sdut.community.controller;

import com.sdut.community.dto.AccessTokenDTO;
import com.sdut.community.dto.GithubUser;
import com.sdut.community.mapper.UserMapper;
import com.sdut.community.model.User;
import com.sdut.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
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
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest request) {
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
            // 写入数据库
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(githubuser.getName());
            user.setAccount_id(String.valueOf(githubuser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            // 登录成功，写cookie和session
            request.getSession().setAttribute("user", user);
        } else {
            // 登录失败，重新登录
        }
        return "redirect:/";
    }
}
