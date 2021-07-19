package com.example.blog.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.google.common.collect.Lists;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AccountEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Column(unique = true)
        @Size(min = 8, max = 30)
        private String accountName;
        @Size(min = 1)
        private String password;
        @Email(message = "Email형식이 아닙니다.")
        private String email;
        @CreationTimestamp
        private LocalDateTime createAt;
        @UpdateTimestamp
        private LocalDateTime UpdatedAt;

        @OneToMany(mappedBy = "accountId", fetch = FetchType.LAZY)
        private List<PostEntity> posts = Lists.newArrayList();

        @OneToMany(mappedBy = "accountId", fetch = FetchType.LAZY)
        private List<CommentEntity> comments = Lists.newArrayList();

        public AccountEntity(String accountName, String password, String email) {
                this.accountName = accountName;
                this.password = password;
                this.email = email;
        }
}
