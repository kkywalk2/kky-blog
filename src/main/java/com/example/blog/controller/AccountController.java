package com.example.blog.controller;

import com.example.blog.dto.AccountDTO;
import com.example.blog.repository.AccountRepository;
import com.example.blog.entity.AccountEntity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("account")
public class AccountController {
    private final AccountRepository accountRepository;

    @Autowired
	AccountController(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }
	
    @PostMapping
    @ResponseBody
    public List<AccountEntity> HelloWorld(@RequestBody AccountDTO req){
    	accountRepository.save(new AccountEntity(req.getAccountName(),req.getPassword(), req.getEmail()));
        return accountRepository.findAll();
    }
}
