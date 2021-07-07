package com.example.blog.controller;

import com.example.blog.dto.*;
import com.example.blog.service.PostService;
import com.example.blog.entity.PostEntity;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("post")
public class PostController {
    private final PostService postService;

	PostController(PostService postService){
        this.postService = postService;
    }

    @PostMapping
    @ResponseBody
    public PostCreateRes signUp(@Valid @RequestBody PostCreateReq req){
        //return postService.createPost(null, req.getContent(), req.getCategory()) ? new PostCreateRes("OK","") : new PostCreateRes("OK","");
        return new PostCreateRes("OK","");
    }
}
