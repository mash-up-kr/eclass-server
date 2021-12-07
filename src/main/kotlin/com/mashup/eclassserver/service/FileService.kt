package com.mashup.eclassserver.service

import com.mashup.eclassserver.supporter.S3Supporter
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class FileService(
    private val s3Supporter: S3Supporter
) {
    fun saveImage(imageFile: MultipartFile?): String {
        val result = imageFile
                ?.let { s3Supporter.transmit(imageFile, S3Supporter.DIARYPICTURES).url }
                ?: throw IllegalStateException("null imageFile")
        return result
    }
}