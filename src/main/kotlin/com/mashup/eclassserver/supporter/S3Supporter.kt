package com.mashup.eclassserver.supporter

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.PutObjectRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class S3Supporter(
    private val awsS3Client: AmazonS3,

    @Value("\${cloud.aws.s3.bucket}")
    private val bucket: String,
) {

    fun transmit(multipartFile: MultipartFile, type: String): ImageUrl {
        val fileName: String = type + "/" + multipartFile.originalFilename
        val putObjectRequest: PutObjectRequest = PutObjectRequest(
            bucket,
            fileName,
            multipartFile.inputStream,
            null
        ).withCannedAcl(CannedAccessControlList.PublicRead)

        awsS3Client.putObject(putObjectRequest)
        return ImageUrl(awsS3Client.getUrl(bucket, fileName).toString())
    }

    companion object {
        const val COVERS: String = "covers"
        const val DIARYPICTURES: String = "diary-pictures"
        const val STICKERS: String = "stickers"
    }
}

data class ImageUrl(
    val url: String
)