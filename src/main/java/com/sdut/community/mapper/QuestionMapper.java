package com.sdut.community.mapper;

import com.sdut.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

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
}
