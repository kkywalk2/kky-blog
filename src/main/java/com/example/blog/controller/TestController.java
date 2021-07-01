package com.example.blog.controller;

import com.example.blog.dto.TestDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Test")
public class TestController {
    @PostMapping
    @ResponseBody
    public TestDTO HelloWorld(@RequestBody TestDTO req){
        return req;
    }
}
