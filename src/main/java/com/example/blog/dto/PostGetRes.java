package com.example.blog.dto;

import java.util.List;

import com.example.blog.entity.PostEntity;

import lombok.Getter;

@Getter
public class PostGetRes extends Response{
    private List<PostEntity> data;
    public PostGetRes(String status, String message, List<PostEntity> data) {
		super(status, message);
        this.data = data;
	}
}
