package com.example.blog.controller;

import com.example.blog.dto.ResponseCode;
import com.example.blog.dto.comment.AddRequest;
import com.example.blog.dto.comment.AddResponse;
import com.example.blog.security.AccountDetail;
import com.example.blog.service.CommentService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    @ResponseBody
    public AddResponse addComment(@RequestBody AddRequest req, Authentication authentication) {
        AccountDetail accountDetail = (AccountDetail) authentication.getPrincipal();
        commentService.addComment(accountDetail.getId(), req.getPostId(), req.getContent());
        return new AddResponse(ResponseCode.OK, "");
    }
}
