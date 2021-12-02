package com.sdut.community.dto;

import lombok.Data;

/**
 * @author 24699
 */
@Data
public class CommentCreateDTO {
    private Long parentId;
    private String content;
    private Integer type;
    private Long Commentator;
}
