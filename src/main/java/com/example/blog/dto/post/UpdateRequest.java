package com.example.blog.dto.post;

import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UpdateRequest {
    long postId;
    @Size(max = 100)
    String title;
    @Size
    String content;
    @Size(min = 1, max = 30)
    String category;
}
