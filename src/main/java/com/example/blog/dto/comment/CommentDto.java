package com.example.blog.dto.comment;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    public Long id;
    public Long postId;
    public String accountId;
    public String content;
    private LocalDateTime createAt;
    private LocalDateTime UpdatedAt;
}
