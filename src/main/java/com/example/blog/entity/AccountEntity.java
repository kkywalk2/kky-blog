package com.example.blog.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

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
        @Email(message = "이메일 형식이 아닙니다")
        private String email;
        @CreatedDate
        private LocalDateTime createAt;
        @LastModifiedDate
        private LocalDateTime UpdatedAt;

        public AccountEntity(String accountName, String password, String email){
                this.accountName = accountName;
                this.password = password;
                this.email = email;
        }
}
