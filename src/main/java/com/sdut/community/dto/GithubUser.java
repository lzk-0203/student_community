package com.sdut.community.dto;

import lombok.Data;

/**
 * @author 24699
 */
@Data
public class GithubUser {
    private String name;
    private Long id;
    private String bio;
    private String avatarUrl;
}
