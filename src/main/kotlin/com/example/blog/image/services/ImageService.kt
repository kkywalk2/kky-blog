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
        val uniqueName = "${UUID.randomUUID()}_${imageFile.originalFilename}"
        val combinedPath = Paths.get(path, uniqueName)
        val bytes = imageFile.bytes

        Files.write(combinedPath, bytes)

        imageRepository.save(
            originalName = imageFile.originalFilename,
            filePath = combinedPath.toString()
        )

        return ImageUploadResponse(uniqueName)
    }

    fun downloadImage(fileName: String): ResponseEntity<Resource> {
        val image = imageRepository.findByFilePath(Paths.get(path, fileName).toString())
        val path = image.getPath()
        val headers = path.toHeaders()

        if(path.notExists()) throw FileNotFoundException()

        val resource: Resource = InputStreamResource(Files.newInputStream(path))

        return ResponseEntity(resource, headers, HttpStatus.OK)
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
