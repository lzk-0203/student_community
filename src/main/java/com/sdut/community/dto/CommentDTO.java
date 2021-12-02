package com.sdut.community.dto;

import com.sdut.community.model.User;
import lombok.Data;

/**
 * @author 24699
 */
@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer likeCount;
    private String content;
    private User user;
}
