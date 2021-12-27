package com.example.blog.service;

import com.example.blog.repository.AccountRepository;

import javax.transaction.Transactional;

import com.example.blog.entity.AccountEntity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void createAccount(String accountName, String password, String email) {
        accountRepository.save(new AccountEntity(accountName, passwordEncoder.encode(password), email));
    }

    public AccountEntity getAccountByName(String accountName) {
        return accountRepository.findByAccountName(accountName);
    }

    public boolean accountValidation(String accountName, String password) {
        AccountEntity account = accountRepository.findByAccountName(accountName);
        return passwordEncoder.matches(password, account.getPassword());
    }
}
