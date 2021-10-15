package com.example.blog.service;

import com.example.blog.dto.post.GetPostsData;
import com.example.blog.dto.post.PostCategories;
import com.example.blog.repository.PostRepository;
import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.List;

import com.example.blog.entity.PostEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

    private final PostRepository postRepository;

    PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public void createPost(Long accountId, String title, String content, String category) {
        postRepository.save(new PostEntity(accountId, title, content, category));
    }

    @Transactional(readOnly = true)
    public Page<GetPostsData> getPosts(Pageable pageable, String query) {
        HashMap<String, String> map = null;

        if (query != null) {
            map = new HashMap<>();
            String[] parameters = query.split(",");
            for (String parameter : parameters) {
                String[] keyValue = parameter.split("=");
                if (keyValue.length == 2) {
                    map.put(keyValue[0], keyValue[1]);
                }
            }
        }
        return postRepository.findAllData(pageable, map);
    }

    @Transactional(readOnly = true)
    public PostEntity getPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new NullPointerException("Unavailable Post"));
    }

    @Transactional(readOnly = true)
    public List<PostCategories> getCategoryCounts() {
        return postRepository.findCategoryCounts();
    }

    @Transactional
    public void updatePost(Long accountId, Long postId, String title, String content, String category) {
        PostEntity postEntity = postRepository.getById(postId);
        Preconditions.checkState(postEntity.getAccountId() == accountId, "Unauthorized");
        postEntity.setTitle(title);
        postEntity.setContent(content);
        postEntity.setCategory(category);
    }

    @Transactional
    public void deletePost(Long accountId, Long postId) {
        PostEntity postEntity = postRepository.getById(postId);
        Preconditions.checkState(postEntity.getAccountId() == accountId, "Unauthorized");
        postRepository.deleteById(postId);
    }
}
