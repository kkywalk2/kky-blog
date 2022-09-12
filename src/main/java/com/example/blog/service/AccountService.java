package com.example.blog.service;

import com.example.blog.dto.account.AccountDto;
import com.example.blog.dto.account.AuthDto;
import com.example.blog.exception.UnauthorizedException;
import com.example.blog.repository.AccountRepository;

import javax.transaction.Transactional;

import com.example.blog.entity.AccountEntity;

import com.example.blog.security.JwtUtil;
import com.google.common.base.Preconditions;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public AccountDto createAccount(String accountName, String password, String email) {
        AccountEntity accountEntity = accountRepository.save(new AccountEntity(accountName, passwordEncoder.encode(password), email));
        return accountEntityToDto(accountEntity);
    }

    public AuthDto accountValidation(String accountName, String password) {
        AccountEntity account = accountRepository.findByAccountName(accountName);
        if(!passwordEncoder.matches(password, account.getPassword())) throw new UnauthorizedException();
        return new AuthDto(account.getAccountName(), jwtUtil.createToken(account.getAccountName()));
    }

    private AccountDto accountEntityToDto(AccountEntity accountEntity) {
        return new AccountDto(
                accountEntity.getAccountName(),
                accountEntity.getEmail(),
                accountEntity.getCreateAt(),
                accountEntity.getUpdatedAt());
    }
}
