package com.example.blog.repository;

import com.example.blog.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
    ImageEntity findByFilePath(String filePath);
}
