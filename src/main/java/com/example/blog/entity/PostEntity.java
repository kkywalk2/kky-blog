package com.example.blog.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
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
    private Long id;
	private Long accountid;
    @Size(max = 3000)
    private String content;
    private Long views;
    @Size(min = 1, max = 30)
    private String category;
    @CreationTimestamp
    private LocalDateTime createAt;
    @UpdateTimestamp
    private LocalDateTime UpdatedAt;
    
    @OneToMany(mappedBy="postid", fetch = FetchType.LAZY)
    private List<CommentEntity> comments = new ArrayList<CommentEntity>();

    public PostEntity(Long accountid, String content, String category){
    		this.accountid = accountid;
            this.content = content;
            this.category = category;
            this.views = 0L;
    }
}
