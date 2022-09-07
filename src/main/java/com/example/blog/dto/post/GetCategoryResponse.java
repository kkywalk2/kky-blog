package com.example.blog.dto.post;

import com.example.blog.dto.Response;
import com.example.blog.dto.ResponseCode;
import lombok.Getter;

import java.util.List;

@Getter
public class GetCategoryResponse extends Response {
    private List<CategoryDto> data;
    public GetCategoryResponse(ResponseCode code, String message, List<CategoryDto> data) {
        super(code, message);
        this.data = data;
    }
}

