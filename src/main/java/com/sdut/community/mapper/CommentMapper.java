package com.sdut.community.mapper;


import com.sdut.community.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
     * 查
     * @param id
     * @return
     */
    @Select("select * from comment where id=#{id}")
    Comment selectById(Long id);
}
