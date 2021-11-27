package com.sdut.community.mapper;

import com.sdut.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author 24699
 */
@Mapper
public interface QuestionMapper {
    /**
     * 添加问题
     * @param question
     * @return
     */
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tags) " +
            "values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tags})")
    int create(Question question);

    /**
     * 搜索问题列表
     * @return
     */
    @Select("select * from question order by id desc")
    List<Question> list();

    /**
     * 计数
     * @return
     */
    @Select("select count(1) from question")
    Integer count();

    /**
     * 获取我的问题
     * @param userId
     * @return
     */
    @Select("select * from question where creator = #{userId}")
    List<Question> getQuestionsByUserId(@Param("userId") Long userId);

    /**
     * 问题分页展示
     * @param userId
     * @return
     */
    @Select("select count(1) from question where creator = #{userId}")
    Integer countByUserId(@Param("userId") Long userId);

    /**
     * 问题显示
     * @param id
     * @return
     */
    @Select("select * from question where id = #{id}")
    Question getQuestionById(@Param("id") Long id);

    /**
     * 修改问题
     * @param question
     * @return
     */
    @Update("update question set title=#{title}, description=#{description}, gmt_modified=#{gmtModified}, tags=#{tags}, " +
            "view_count=#{viewCount} where id=#{id}")
    int update(Question question);

    /**
     * 用于数据库自增添加浏览数
     * @param question
     */
    @Update("update question set view_count=view_count + 1 where id=#{id}")
    void updateByView(Question question);

    /**
     * 添加问题评论数
     * @param question
     */
    @Update("update question set comment_count=1 + #{commentCount} where id=#{id}")
    void updateByComment(Question question);
}
