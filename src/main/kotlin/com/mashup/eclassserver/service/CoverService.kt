package com.mashup.eclassserver.service

import com.mashup.eclassserver.exception.EclassException
import com.mashup.eclassserver.exception.ErrorCode
import com.mashup.eclassserver.model.dto.AttachedStickerDto
import com.mashup.eclassserver.model.dto.CoverData
import com.mashup.eclassserver.model.dto.CoverResponseDto
import com.mashup.eclassserver.model.entity.AttachedSticker
import com.mashup.eclassserver.model.entity.AttachedType
import com.mashup.eclassserver.model.entity.Cover
import com.mashup.eclassserver.model.repository.AttachedStickerRepository
import com.mashup.eclassserver.model.repository.CoverRepository
import com.mashup.eclassserver.model.repository.MemberRepository
import com.mashup.eclassserver.model.repository.StickerRepository
import com.mashup.eclassserver.supporter.S3Supporter
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate

@Service
class CoverService(
    private val s3Supporter: S3Supporter,
    private val coverRepository: CoverRepository,
    private val attachedStickerRepository: AttachedStickerRepository,
    private val stickerRepository: StickerRepository
) {

    fun homeByMonth(petId: Long, targetDate: String): CoverResponseDto {
        val cover = coverRepository.findByPetIdAndTargetDate(petId, targetDate)
        val attachedStickerList = attachedStickerRepository.findAttachedStickerWithStickerByAttachedIdAndAttachedType(cover.coverId, AttachedType.COVER)

        return CoverResponseDto(cover.imageUrl, attachedStickerList)
    }

    @Transactional
    fun register(memberId: Long, petId: Long, coverData: CoverData, imageFile: MultipartFile) {

        validateStickerId(coverData.attachedStickerList)

        val imageUrl = s3Supporter.transmit(imageFile, S3Supporter.COVERS)
        val cover = coverRepository.save(
            Cover(
                petId = petId,
                targetDate = coverData.targetDate,
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

    private fun validateStickerId(attachedStickerList: List<AttachedStickerDto>) {
        val attachedStickerStickerIdList = attachedStickerList.map { it.stickerId }
        val stickerList = stickerRepository.findByStickerIdIn(attachedStickerStickerIdList)

        if (stickerList.count() != attachedStickerStickerIdList.count()) {
            val badStickerIdList = attachedStickerStickerIdList.filterNot {
                it in stickerList.map { sticker ->
                    sticker.stickerId
                }
            }

            throw EclassException(ErrorCode.INVALID_INPUT_VALUE, "invalid stickerId -> bad stickerId list:$badStickerIdList")
        }
    }
}