package com.example.blog.entity;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PostEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;
	private Long accountUid;
    @Size(max = 3000)
    private String content;
    private Long views;
    private String type;
    @CreationTimestamp
    private LocalDateTime createAt;
    @UpdateTimestamp
    private LocalDateTime UpdatedAt;
    
    @OneToMany(mappedBy="postUid")
    private Set<CommentEntity> comments;

    public PostEntity(Long accountUid, String content, String type){
    		this.accountUid = accountUid;
            this.content = content;
            this.type = type;
    }
}
