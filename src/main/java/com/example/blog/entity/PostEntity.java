package com.example.blog.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Size;

import com.google.common.collect.Lists;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE post_entity SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@Table(indexes = {
    @Index(name = "title_index", columnList = "title"),
  })
public class PostEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long accountId;
    @Size(max = 100)
    private String title;
    @Size
    private String content;
    private Long views;
    @Size(min = 1, max = 30)
    private String category;
    @CreationTimestamp
    private LocalDateTime createAt;
    @UpdateTimestamp
    private LocalDateTime UpdatedAt;
    private boolean deleted = Boolean.FALSE;

    @OneToMany(mappedBy="postId", fetch = FetchType.EAGER)
    private List<CommentEntity> comments = Lists.newArrayList();

    public PostEntity(long accountId, String title, String content, String category){
    		this.accountId = accountId;
    		this.title = title;
            this.content = content;
            this.category = category;
            this.views = 0L;
    }
}
