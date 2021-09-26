package com.example.blog;

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

import static org.mockito.BDDMockito.given; 

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {AccountService.class, BCryptPasswordEncoder.class, AccountRepository.class})
public class AccountServiceTests {

    AccountService accountService;

    PasswordEncoder passwordEncoder;

    @MockBean
    AccountRepository accountRepository;

    @BeforeEach
    public void initService() {
        passwordEncoder = new BCryptPasswordEncoder();
        accountService = new AccountService(accountRepository, passwordEncoder);
    }

    @Test
    public void accountServiceTest() {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAccountName("kkywalk2");
        accountEntity.setPassword(passwordEncoder.encode("pruna333"));
        accountEntity.setEmail("kkywalk2@gmail.com");

        given(accountRepository.findByAccountName("kkywalk2")).willReturn(accountEntity);

        Assertions.assertEquals(true, accountService.accountValidation("kkywalk2","pruna333"));
    }
}
