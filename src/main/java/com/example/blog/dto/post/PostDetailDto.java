package com.example.blog.dto.post;

import com.example.blog.dto.comment.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class PostDetailDto {
    private Long id;
    private String title;
    private String category;
    private Long views;
    private LocalDateTime createAt;
    private List<CommentDto> comments;
}
