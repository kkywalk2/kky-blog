package com.example.blog.security;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import com.example.blog.repository.AccountRepository;
import com.example.blog.entity.AccountEntity;

@Service
public class AccountDetailService implements UserDetailsService {

    private final AccountRepository accountRepository;

    AccountDetailService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String accountName) throws UsernameNotFoundException {
        AccountEntity account = accountRepository.findByAccountName(accountName);
        Preconditions.checkState(account != null, "user name not found!");
        return new org.springframework.security.core.userdetails.User(account.getAccountName(),account.getPassword(),new ArrayList<>());
    }
}