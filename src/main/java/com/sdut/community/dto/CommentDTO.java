package com.sdut.community.dto;

import lombok.Data;

/**
 * @author 24699
 */
@Data
public class CommentDTO {
    private Long parentId;
    private String content;
    private Integer type;
    private Long Commentator;
}
