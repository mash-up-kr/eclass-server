package com.mashup.eclassserver.service

import com.mashup.eclassserver.model.dto.ImageUrlResponseDto
import com.mashup.eclassserver.supporter.S3Supporter
import kotlinx.coroutines.*
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class FileService(
    private val s3Supporter: S3Supporter
) {
    fun saveImages(imageFiles: List<MultipartFile>): List<ImageUrlResponseDto> {
        val imageUrlResponseDtoList = mutableListOf<ImageUrlResponseDto>()

        runBlocking {
            imageFiles.map { multipartFile ->
                GlobalScope.async {
                    imageUrlResponseDtoList.add(ImageUrlResponseDto(s3Supporter.transmit(multipartFile, S3Supporter.DIARYPICTURES).url))
                }
            }.joinAll()
        }

        return imageUrlResponseDtoList
    }
}