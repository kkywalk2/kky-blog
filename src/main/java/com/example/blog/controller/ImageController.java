package com.example.blog.controller;

import org.apache.tomcat.util.file.ConfigurationSource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import sun.misc.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("image")
public class ImageController {
    @ResponseBody
    @GetMapping
    public ResponseEntity<Resource> testPhoto() throws IOException {
        Path path = Paths.get("C:/Users/KKY/OneDrive/Desktop/PNG_transparency_demonstration_1.png");
        String contentType = Files.probeContentType(path);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename("PNG_transparency_demonstration_1.png", StandardCharsets.UTF_8)
                .build());

        Resource resource = new InputStreamResource(Files.newInputStream(path));
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}
