package com.mashup.eclassserver.service

import com.mashup.eclassserver.model.dto.CoverData
import com.mashup.eclassserver.model.entity.AttachedSticker
import com.mashup.eclassserver.model.entity.AttachedType
import com.mashup.eclassserver.model.entity.Cover
import com.mashup.eclassserver.model.repository.AttachedStickerRepository
import com.mashup.eclassserver.model.repository.CoverRepository
import com.mashup.eclassserver.supporter.S3Supporter
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class CoverService(
    private val s3Supporter: S3Supporter,
    private val coverRepository: CoverRepository,
    private val attachedStickerRepository: AttachedStickerRepository
) {

    @Transactional
    fun register(memberId: Long, petId: Long, coverData: CoverData, imageFile: MultipartFile) {
        val imageUrl = s3Supporter.transmit(imageFile, S3Supporter.COVERS)
        val cover = coverRepository.save(
            Cover(
                petId = petId,
                imageUrl = imageUrl.url,
                color = coverData.color,
                shapeType = coverData.shapeType,
                shapeX = coverData.shapeX,
                shapeY = coverData.shapeY
            )
        )

        val attachedStickerList = coverData.attachedStickerList.map {
            AttachedSticker.of(memberId, cover.coverId, AttachedType.COVER, it)
        }

        attachedStickerRepository.saveAll(attachedStickerList)
    }
}