package com.example.blog.dto.account;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AccountDto {
    public String name;
    public String email;
    private LocalDateTime createAt;
    private LocalDateTime UpdatedAt;
}
