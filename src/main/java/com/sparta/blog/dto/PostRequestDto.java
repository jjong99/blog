package com.sparta.blog.dto;

import lombok.Getter;

@Getter
public class PostRequestDto {
    private Long id;
    private String username;
    private String password;
    private String title;
    private String contents;
}
