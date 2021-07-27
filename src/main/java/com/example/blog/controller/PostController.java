package com.example.blog.controller;

import com.example.blog.dto.ResponseCode;
import com.example.blog.dto.post.*;
import com.example.blog.security.AccountDetail;
import com.example.blog.service.PostService;

import javax.validation.Valid;

import org.springframework.security.core.Authentication;
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
        AccountDetail accountDetail = (AccountDetail) authentication.getPrincipal();
        postService.createPost(accountDetail.getId(), req.getTitle(), req.getContent(), req.getCategory());
        return new CreateResponse(ResponseCode.OK, "");
    }

    @GetMapping
    @ResponseBody
    public GetPostsResponse getPosts(Authentication authentication) {
        AccountDetail accountDetail = (AccountDetail) authentication.getPrincipal();
        return new GetPostsResponse(ResponseCode.OK, "", postService.getPosts(accountDetail.getId()));
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public GetPostResponse getPost(@PathVariable("id") Long id, Authentication authentication) {
        AccountDetail accountDetail = (AccountDetail) authentication.getPrincipal();
        return new GetPostResponse(ResponseCode.OK, "", postService.getPost(accountDetail.getId(), id));
    }

    @GetMapping(value = "/category")
    @ResponseBody
    public GetCategoryResponse getPost(Authentication authentication) {
        AccountDetail accountDetail = (AccountDetail) authentication.getPrincipal();
        return new GetCategoryResponse(ResponseCode.OK, "", postService.getCategoryCounts(accountDetail.getId()));
    }

    @GetMapping(value = "/category/{category}")
    @ResponseBody
    public GetPostsResponse getPost(@PathVariable("category") String category, Authentication authentication) {
        AccountDetail accountDetail = (AccountDetail) authentication.getPrincipal();
        return new GetPostsResponse(ResponseCode.OK, "", postService.getPosts(accountDetail.getId(), category));
    }
}
