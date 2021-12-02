package com.sdut.community.mapper;


import com.sdut.community.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 24699
 */
@Mapper
public interface CommentMapper {
    /**
     * 插入评论
     * @param comment
     */
    @Insert("insert into comment (id,parent_id,type,commentator,gmt_create,gmt_modified,like_count,content) " +
            "values (#{id},#{parentId},#{type},#{commentator},#{gmtCreate},#{gmtModified},#{likeCount},#{content})")
    void insert(Comment comment);

    /**
     * 查评论
     * @param id
     * @return
     */
    @Select("select * from comment where id=#{id}")
    Comment selectById(Long id);

    /**
     * 查找不同级别评论
     * @param id
     * @param type
     * @return
     */
    @Select("select * from comment where parent_id=#{id} and type=#{type}")
    List<Comment> questionsByIdAndType(Long id, Integer type);
}
