package com.sdut.community.provider;

import com.alibaba.fastjson.JSON;
import com.sdut.community.dto.AccessTokenDTO;
import com.sdut.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author 24699
 */
@Component
public class GithubProvider {
    /**
     * 利用githubAPI获取授权登录的token
     * 1.携带 accessTokenDTO(Json) 发送请求
     * 2.响应中获取 accessToken，截取 token
     * @param accessTokenDTO
     * 传送类
     * @return github中的 token
     */
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient(); 

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
            // access_token=ge3runIL&scope=user&token_type=bearer
            String token = str.split("&")[0].split("=")[1];
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 利用githubAPI发送token，获取用户信息
     * 1.携带 token 发送请求
     * 2.获取用户信息，转成Json格式
     * @param accessToken
     * github的token
     * @return githubUser
     */
    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?")
                .header("Authorization","token "+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String str = response.body().string();
            GithubUser githubUser = JSON.parseObject(str, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
        }
        return null;
    }
}
