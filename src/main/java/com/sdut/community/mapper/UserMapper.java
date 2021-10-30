package com.sdut.community.mapper;

import com.sdut.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 24699
 */
@Mapper
public interface UserMapper {
    /**
     * 插入User，返回-1 或 0
     * @param user
     * @return
     */
    @Insert("insert into user (name, account_id, token, gmt_create, gmt_modified) " +
            "values (#{name}, #{account_id}, #{token}, #{gmtCreate}, #{gmtModified})")
    int insert(User user);
}
