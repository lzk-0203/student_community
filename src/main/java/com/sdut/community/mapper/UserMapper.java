package com.sdut.community.mapper;

import com.sdut.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author 24699
 */
@Mapper
public interface UserMapper {
    /**
     * 利用注解整合mybatis，插入User
     * @param user
     * @return -1 or 0
     */
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,avatar_url) " +
            "values (#{name}, #{account_id}, #{token}, #{gmtCreate}, #{gmtModified}, #{avatarUrl})")
    int insert(User user);

    /**
     * 根据自己生成的token查找user
     * @param token
     * 利用UUID生成的本地 token
     * @return User
     */
    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token") String token);
}
