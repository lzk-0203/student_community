package com.sdut.community.dto;

import com.sdut.community.model.User;
import lombok.Data;

/**
 * @author 24699
 */
@Data
public class  QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private String tags;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private User user;
}
