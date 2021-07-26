package com.example.blog.dto.comment;

import lombok.Data;

@Data
public class AddRequest {
    private Long postId;
    private String content;
}
