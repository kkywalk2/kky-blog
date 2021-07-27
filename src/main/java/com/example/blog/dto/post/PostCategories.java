package com.example.blog.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostCategories {
    private String category;
    private Long count;
}
