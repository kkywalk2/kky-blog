package com.example.blog.image.services

import com.example.blog.dto.ImageUploadResponse
import com.example.blog.exception.FileNotFoundException
import com.example.blog.image.infrastructures.ImageRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.http.ContentDisposition
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import kotlin.io.path.name
import kotlin.io.path.notExists

@Service
@Transactional
class ImageService(
    private val imageRepository: ImageRepository,
    @Value("\${path.image}") private val path: String
) {

    fun saveImage(imageFile: MultipartFile): ImageUploadResponse {
        // Validate file is not empty
        if (imageFile.isEmpty) {
            throw IllegalArgumentException("File is empty")
        }

        // Validate file size (max 5MB)
        val maxFileSize = 5 * 1024 * 1024 // 5MB in bytes
        if (imageFile.size > maxFileSize) {
            throw IllegalArgumentException("File size exceeds maximum allowed size of 5MB")
        }

        // Validate content type - only allow image types
        val allowedContentTypes = setOf("image/jpeg", "image/png", "image/gif", "image/webp")
        val contentType = imageFile.contentType
        if (contentType == null || contentType !in allowedContentTypes) {
            throw IllegalArgumentException("Invalid file type. Only JPEG, PNG, GIF, and WebP images are allowed")
        }

        // Validate original filename
        val originalFilename = imageFile.originalFilename ?: throw IllegalArgumentException("Filename is required")
        validateFileName(originalFilename)

        // Validate file extension matches content type
        val extension = originalFilename.substringAfterLast('.', "").lowercase()
        val expectedExtensions = when (contentType) {
            "image/jpeg" -> setOf("jpg", "jpeg")
            "image/png" -> setOf("png")
            "image/gif" -> setOf("gif")
            "image/webp" -> setOf("webp")
            else -> emptySet()
        }
        if (extension !in expectedExtensions) {
            throw IllegalArgumentException("File extension does not match content type")
        }

        val uniqueName = "${UUID.randomUUID()}_${originalFilename}"
        val combinedPath = Paths.get(path, uniqueName)
        val bytes = imageFile.bytes

        Files.write(combinedPath, bytes)

        imageRepository.save(
            originalName = originalFilename,
            filePath = combinedPath.toString()
        )

        return ImageUploadResponse(uniqueName)
    }

    fun downloadImage(fileName: String): ResponseEntity<Resource> {
        // Validate fileName to prevent path traversal attacks
        validateFileName(fileName)

        val image = imageRepository.findByFilePath(Paths.get(path, fileName).toString())
        val path = image.getPath()
        val headers = path.toHeaders()

        if(path.notExists()) throw FileNotFoundException()

        val resource: Resource = InputStreamResource(Files.newInputStream(path))

        return ResponseEntity(resource, headers, HttpStatus.OK)
    }

    private fun validateFileName(fileName: String) {
        // Reject filenames containing path traversal characters
        if (fileName.contains("..") || fileName.contains("/") || fileName.contains("\\")) {
            throw IllegalArgumentException("Invalid filename: path traversal characters not allowed")
        }

        // Only allow alphanumeric, dash, underscore, and dot characters
        val validFileNamePattern = Regex("^[a-zA-Z0-9._-]+$")
        if (!fileName.matches(validFileNamePattern)) {
            throw IllegalArgumentException("Invalid filename: only alphanumeric, dash, underscore, and dot characters are allowed")
        }
    }

    private fun Path.toHeaders(): HttpHeaders {
        val contentType = Files.probeContentType(this)

        val headers = HttpHeaders()
        headers.add(HttpHeaders.CONTENT_TYPE, contentType)
        headers.contentDisposition = ContentDisposition
            .builder("attachment")
            .filename(this.name, StandardCharsets.UTF_8)
            .build()

        return headers
    }
}
