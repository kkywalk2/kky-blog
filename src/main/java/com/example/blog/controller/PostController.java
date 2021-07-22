package com.example.blog.controller;

import com.example.blog.dto.ResponseCode;
import com.example.blog.dto.post.CreateRequest;
import com.example.blog.dto.post.CreateResponse;
import com.example.blog.dto.post.GetResponse;
import com.example.blog.service.PostService;

import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("post")
public class PostController {
    private final PostService postService;

    PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    @ResponseBody
    public CreateResponse createPost(@Valid @RequestBody CreateRequest req, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        postService.createPost(userDetails, req.getTitle(), req.getContent(), req.getCategory());
        return new CreateResponse(ResponseCode.OK, "");
    }

    @GetMapping
    @ResponseBody
    public GetResponse getPosts(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return new GetResponse(ResponseCode.OK, "", postService.getPosts(userDetails));
    }
}
