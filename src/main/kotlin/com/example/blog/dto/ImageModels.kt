package com.example.blog.dto

import org.springframework.web.multipart.MultipartFile

data class ImageUploadRequest(
    val imageFile: MultipartFile
)

data class ImageUploadResponse(
    val fileName: String
)
