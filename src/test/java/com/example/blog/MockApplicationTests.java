package com.example.blog;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.blog.dto.post.GetPostsData;
import com.example.blog.entity.AccountEntity;
import com.example.blog.repository.AccountRepository;
import com.example.blog.service.AccountService;
import com.example.blog.service.PostService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {AccountService.class})
public class MockApplicationTests {

    //@Mock
    //PostService postService;

    @Autowired
    AccountService accountService;
    
    @MockBean(name = "accountRepository")
    AccountRepository accountRepository;

    @MockBean(name = "passwordEncoder")
    PasswordEncoder passwordEncoder;

    @Test
    public void accountServiceTest() {
        accountService.createAccount("kkywalk2", "pruna333", "kkywalk2@gmail.com");
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAccountName("kkywalk2");
        Mockito.when(accountService.getAccountByName("kkywalk2")).thenReturn(accountEntity);

        Assertions.assertEquals("kkywalk2", accountEntity.getAccountName());
    }

    /*@Test
    public void postServiceTest() {
        AccountEntity account = accountService.getAccountByName("kkywalk2");
        postService.createPost(account.getId(), "test", "test", "test");

        Assertions.assertEquals(1, postService.getCategoryCounts());

        Page<GetPostsData> pages = postService.getPosts(Pageable.unpaged());
        Assertions.assertEquals("test", pages.getContent().get(0).getTitle());
    }*/
}
