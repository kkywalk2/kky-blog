package com.example.blog.controller;

import com.example.blog.dto.ResponseCode;
import com.example.blog.dto.post.*;
import com.example.blog.security.AccountDetail;
import com.example.blog.service.PostService;

import javax.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public GetPostsResponse getPosts(@RequestParam(required = true, value = "page", defaultValue = "0") int page,
            @RequestParam(required = true, value = "per_page", defaultValue = "0") int perPage,
            @RequestParam(required = false, value = "query") String query) {
        Pageable pageable = perPage == 0 ? Pageable.unpaged() : PageRequest.of(page, perPage);
        return new GetPostsResponse(ResponseCode.OK, "", postService.getPosts(pageable, query));
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public GetPostResponse getPost(@PathVariable("id") Long id) {
        return new GetPostResponse(ResponseCode.OK, "", postService.getPost(id));
    }

    @GetMapping(value = "/category")
    @ResponseBody
    public GetCategoryResponse getPost() {
        return new GetCategoryResponse(ResponseCode.OK, "", postService.getCategoryCounts());
    }

    @PutMapping
    @ResponseBody
    public CreateResponse updatePost(@Valid @RequestBody UpdateRequest req, Authentication authentication) {
        AccountDetail accountDetail = (AccountDetail) authentication.getPrincipal();
        postService.updatePost(accountDetail.getId(), req.getPostId(), req.getTitle(), req.getContent(),
                req.getCategory());
        return new CreateResponse(ResponseCode.OK, "");
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public DeleteResponse deletePost(@PathVariable("id") Long id, Authentication authentication) {
        AccountDetail accountDetail = (AccountDetail) authentication.getPrincipal();
        postService.deletePost(accountDetail.getId(), id);
        return new DeleteResponse(ResponseCode.OK, "");
    }
}
