package com.mashup.eclassserver.controller

import com.mashup.eclassserver.model.dto.ImageUrlResponseDto
import com.mashup.eclassserver.service.FileService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/file")
class FileController(
    private val awsService: FileService
) {
    @PostMapping("/image")
    fun registerImage(@RequestParam imageFiles: List<MultipartFile>): ResponseEntity<List<ImageUrlResponseDto>> {
        return ResponseEntity.status(HttpStatus.OK).body(awsService.saveImages(imageFiles))
    }
}