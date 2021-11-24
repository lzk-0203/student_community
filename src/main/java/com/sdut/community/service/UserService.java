package com.sdut.community.service;

import com.sdut.community.mapper.UserMapper;
import com.sdut.community.model.User;
<<<<<<< HEAD
import com.sdut.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

=======
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

>>>>>>> bafc6151b975265c04572e50e92c2faf8724cdd0
/**
 * @author 24699
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
<<<<<<< HEAD
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(user.getAccountId());
        List<User> dbUsers = userMapper.selectByExample(userExample);
        if (dbUsers.size() == 0) {
=======
        User dbUser = userMapper.findByAccountId(user.getAccountId());
        if (dbUser == null) {
>>>>>>> bafc6151b975265c04572e50e92c2faf8724cdd0
            // 插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        } else {
            // 插入
<<<<<<< HEAD
            User dbUser = dbUsers.get(0);

            User updateUser = new User();
            updateUser.setGmtModified(System.currentTimeMillis());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setName(user.getName());
            updateUser.setToken(user.getToken());

            UserExample example = new UserExample();
            example.createCriteria()
                    .andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updateUser, example);
=======
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());
            userMapper.update(dbUser);
>>>>>>> bafc6151b975265c04572e50e92c2faf8724cdd0
        }
    }
}
