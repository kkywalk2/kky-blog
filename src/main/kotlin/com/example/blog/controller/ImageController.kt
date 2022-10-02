package com.example.blog.controller;

import com.example.blog.dto.ResponseCode;
import com.example.blog.dto.image.UploadRequest;
import com.example.blog.dto.image.UploadResponse;
import com.example.blog.service.ImageService;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("image")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService){
        this.imageService = imageService;
    }

    @ResponseBody
    @GetMapping(value = "/{fileName}")
    public ResponseEntity<Resource> downloadImage(@PathVariable("fileName") String fileName) throws IOException {
        return imageService.downloadImage(fileName);
    }

    @ResponseBody
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public UploadResponse uploadImage(@Valid @ModelAttribute UploadRequest req) throws IOException {
        return new UploadResponse(ResponseCode.OK,"", imageService.saveImage(req.getImageFile()));
    }
}
