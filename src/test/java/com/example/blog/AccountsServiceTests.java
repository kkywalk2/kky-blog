package com.example.blog;

import com.example.blog.dto.AuthDto;
import com.example.blog.dto.SignInRequest;
import com.example.blog.security.JwtUtil;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.blog.entity.AccountEntity;
import com.example.blog.repository.AccountRepository;
import com.example.blog.service.AccountService;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {AccountService.class, BCryptPasswordEncoder.class, AccountRepository.class})
public class AccountsServiceTests {

    AccountService accountService;

    PasswordEncoder passwordEncoder;

    @MockBean
    AccountRepository accountRepository;

    @MockBean
    JwtUtil jwtUtil;

    @BeforeEach
    public void initService() {
        passwordEncoder = new BCryptPasswordEncoder();
        accountService = new AccountService(accountRepository, passwordEncoder, jwtUtil);
    }

    @Test
    public void accountServiceTest() {
        AccountEntity accountEntity = new AccountEntity(
                1,
                "kkywalk2",
                passwordEncoder.encode("pruna333"),
                "kkywalk2@gmail.com",
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        given(accountRepository.findByAccountName("kkywalk2")).willReturn(Optional.of(accountEntity));

        given(jwtUtil.createToken("kkywalk2")).willReturn("token");

        Assertions.assertEquals(new AuthDto("kkywalk2", "token"), accountService.accountValidation(new SignInRequest("kkywalk2","pruna333")));
    }
}
