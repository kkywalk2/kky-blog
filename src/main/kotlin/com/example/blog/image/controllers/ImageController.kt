package com.example.blog.image.controllers

import com.example.blog.dto.ImageUploadRequest
import com.example.blog.dto.ImageUploadResponse
import com.example.blog.image.services.ImageService
import jakarta.validation.Valid
import org.springframework.core.io.Resource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/image")
class ImageController(
    private val imageService: ImageService
) {

    @GetMapping(value = ["/{fileName}"])
    fun downloadImage(@PathVariable("fileName") fileName: String): ResponseEntity<Resource> {
        return imageService.downloadImage(fileName)
    }

    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun uploadImage(@ModelAttribute req: @Valid ImageUploadRequest): ImageUploadResponse {
        return imageService.saveImage(req.imageFile)
    }
}
