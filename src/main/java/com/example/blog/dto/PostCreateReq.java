package com.example.blog.dto;

import lombok.Data;

@Data
public class PostCreateReq {
    String content;
    String category;
}
