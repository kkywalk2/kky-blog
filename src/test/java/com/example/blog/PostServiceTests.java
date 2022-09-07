package com.example.blog;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.blog.repository.PostRepository;
import com.example.blog.service.PostService;
import com.example.blog.dto.post.PostDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {PostService.class, PostRepository.class})
public class PostServiceTests {

    @Autowired
    PostService postService;

    @MockBean
    PostRepository postRepository;

    @Test
    public void postServiceTest() {
        List<PostDto> result = new ArrayList<>();
        result.add(new PostDto(1L, "test1", "test", 0L, LocalDateTime.now()));
        result.add(new PostDto(2L, "test2", "test", 0L, LocalDateTime.now()));
        result.add(new PostDto(3L, "test3", "test", 0L, LocalDateTime.now()));

        Page<PostDto> pages = new PageImpl(result, PageRequest.of(0, 3), 3);

        given(postRepository.findAllData(PageRequest.of(0, 3), Optional.empty(), Optional.empty())).willReturn(pages);

        Assertions.assertEquals("test1", postService.getPosts(PageRequest.of(0, 3), Optional.empty(), Optional.empty()).getContent().get(0).getTitle());
    }
}
