package com.example.blog.service;

import com.example.blog.repository.AccountRepository;

import javax.transaction.Transactional;

import com.example.blog.entity.AccountEntity;

import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @Transactional
    public boolean createAccount(String accountName, String password, String email) {
        AccountEntity account = accountRepository.save(new AccountEntity(accountName, password, email));
        return accountRepository.existsById(account.getId());
    }
}
