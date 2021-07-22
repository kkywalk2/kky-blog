package com.example.blog.dto.post;

import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CreateRequest {
    @Size(max = 100)
    String title;
    @Size(max = 3000)
    String content;
    @Size(min = 1, max = 30)
    String category;
}
