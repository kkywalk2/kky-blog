package com.example.blog.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class HomeController {

    @GetMapping("/**/{path:[^\\\\.]*}")
    fun index(): String {
        return "forward:/"
    }
}
