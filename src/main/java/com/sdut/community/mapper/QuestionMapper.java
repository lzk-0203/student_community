package com.sdut.community.mapper;

import com.sdut.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import javax.persistence.criteria.CriteriaBuilder;
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
    @Select("select * from question")
    List<Question> list();

    /**
     * 计数
     * @return
     */
    @Select("select count(1) from question")
    Integer count();
}
