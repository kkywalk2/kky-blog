package com.example.blog.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CommentEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;
	private Long accountUid;
	private Long postUid;
	private String content;
    @CreationTimestamp
    private LocalDateTime createAt;
    @UpdateTimestamp
    private LocalDateTime UpdatedAt;
    
    public CommentEntity(Long accountUid,Long postUid,String content) {
    	this.accountUid = accountUid;
    	this.postUid = postUid;
    	this.content = content;
    }
}
