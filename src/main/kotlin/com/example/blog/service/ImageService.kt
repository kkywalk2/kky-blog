package com.example.blog.service;

import com.example.blog.entity.ImageEntity;
import com.example.blog.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageService {

    @Value("${path.image}")
    private String path;
    private final ImageRepository imageRepository;

    ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public String saveImage(MultipartFile imageFile) throws IOException {
        String uniqueName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
        Path combinedPath = Paths.get(path, uniqueName);
        byte[] bytes = imageFile.getBytes();
        Files.write(combinedPath, bytes);
        imageRepository.save(new ImageEntity(imageFile.getOriginalFilename(),combinedPath.toString()));
        return uniqueName;
    }

    public ResponseEntity<Resource> downloadImage(String fileName) throws IOException {
        ImageEntity image = imageRepository.findByFilePath(Paths.get(path, fileName).toString());
        Path path = Paths.get(image.getFilePath());
        String contentType = Files.probeContentType(path);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename(image.getOriginalName(), StandardCharsets.UTF_8)
                .build());

        Resource resource = new InputStreamResource(Files.newInputStream(path));
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}
