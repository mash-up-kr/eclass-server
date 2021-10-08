package com.mashup.eclassserver.supporter

import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class S3Supporter {

    fun transmit(multipartFile: MultipartFile): ImageUrl {
        // todo S3 파일 전송 기능
        return ImageUrl("https://user-images.githubusercontent.com/18495291/135726037-c0d395ff-bfd6-41de-8bf0-c9e7c7f620e8.jpeg")
    }
}

data class ImageUrl(
    val url: String
)