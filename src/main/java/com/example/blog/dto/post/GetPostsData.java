package com.example.blog.dto.post;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetPostsData {
    private Long id;
    private String title;
    private String category;
    private Long views;
    private LocalDateTime createAt;
}
