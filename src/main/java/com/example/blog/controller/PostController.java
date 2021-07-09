package com.example.blog.controller;

import com.example.blog.dto.*;
import com.example.blog.service.PostService;

import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("post")
public class PostController {
    private final PostService postService;

    PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    @ResponseBody
    public PostCreateRes createPost(@Valid @RequestBody PostCreateReq req, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        postService.createPost(userDetails, req.getContent(), req.getCategory());
        return new PostCreateRes("OK", "");
    }

    @GetMapping
    @ResponseBody
    public PostGetRes getPosts(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return new PostGetRes("OK", "", postService.getPosts(userDetails));
    }
}
