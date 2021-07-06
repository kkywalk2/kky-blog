package com.example.blog.entity;

import javax.persistence.Entity;
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
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AccountEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long uid;
        @Size(min = 8, max = 30)
        private String accountName;
        @Size(min = 8, max = 30)
        private String password;
        @Email(message = "�씠硫붿씪 �삎�떇�씠 �븘�떃�땲�떎")
        private String email;
        @CreationTimestamp
        private LocalDateTime createAt;
        @UpdateTimestamp
        private LocalDateTime UpdatedAt;
        
        @OneToMany(mappedBy="accountUid")
        private Set<PostEntity> posts;
        
        @OneToMany(mappedBy="accountUid")
        private Set<CommentEntity> comments;

        public AccountEntity(String accountName, String password, String email){
                this.accountName = accountName;
                this.password = password;
                this.email = email;
        }
}
