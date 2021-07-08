package com.example.blog.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AccountEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Size(min = 8, max = 30)
        private String accountName;
        @Size(min = 8, max = 30)
        private String password;
        @Email(message = "Email형식이 아닙니다.")
        private String email;
        @CreationTimestamp
        private LocalDateTime createAt;
        @UpdateTimestamp
        private LocalDateTime UpdatedAt;

        @OneToMany(mappedBy = "accountid", fetch = FetchType.LAZY)
        private List<PostEntity> posts = new ArrayList<PostEntity>();

        @OneToMany(mappedBy = "accountid", fetch = FetchType.LAZY)
        private List<CommentEntity> comments = new ArrayList<CommentEntity>();

        public AccountEntity(String accountName, String password, String email) {
                this.accountName = accountName;
                this.password = password;
                this.email = email;
        }

        public void addPost(PostEntity post) {
                if (posts == null) {
                        posts = new ArrayList<PostEntity>();
                }
                posts.add(post);
        }

        /*public ArrayList<PostEntity> getPosts() {
                ArrayList<PostEntity> list = new ArrayList<PostEntity>();
                list.addAll(posts);
                return list;
        }*/
}
