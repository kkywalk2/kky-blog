package com.example.blog.controller;

import com.example.blog.dto.UserDTO;
import com.example.blog.repository.UserRepository;
import com.example.blog.entity.UserEntity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {
	@Autowired
	UserRepository userRepository;
	
    @PostMapping
    @ResponseBody
    public List<UserEntity> HelloWorld(@RequestBody UserDTO req){
    	userRepository.save(new UserEntity(null, req.getAccountName(),req.getPassword()));
        return userRepository.findAll();
    }
}
