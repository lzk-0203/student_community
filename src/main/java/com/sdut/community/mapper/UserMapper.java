package com.sdut.community.mapper;

import com.sdut.community.model.User;
import org.apache.ibatis.annotations.*;

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
            "values (#{name}, #{accountId}, #{token}, #{gmtCreate}, #{gmtModified}, #{avatarUrl})")
    int insert(User user);

    /**
     * 根据本地token查user
     * @param token
     * 利用UUID生成的本地 token
     * @return User
     */
    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token") String token);

    /**
     * 根据Id查user
     * @param id
     * @return
     */
    @Select("select * from user where id=#{id}")
    User findById(@Param("id") Long id);

    /**
     * 避免用户二次添加
     * @param accountId
     * @return
     */
    @Select("select * from user where account_id=#{account_id}")
    User findByAccountId(@Param("account_id") String accountId);

    /**
     * 跟新用户
     * @param dbUser
     */
    @Update("update user set name=#{name}, token=#{token}, gmt_modified=#{gmtModified}, avatar_url=#{avatarUrl} " +
            "where id = #{id}")
    void update(User dbUser);
}
