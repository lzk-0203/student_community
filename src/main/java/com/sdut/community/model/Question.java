package com.sdut.community.model;

import lombok.Data;

/**
 * @author 24699
 */
@Data
public class Question {
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
}
