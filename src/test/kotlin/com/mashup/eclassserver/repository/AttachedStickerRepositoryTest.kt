package com.mashup.eclassserver.repository

import com.mashup.eclassserver.model.entity.*
import com.mashup.eclassserver.model.repository.AttachedStickerRepository
import com.mashup.eclassserver.model.repository.CoverRepository
import com.mashup.eclassserver.model.repository.StickerRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDate

@RepositoryTest
class AttachedStickerRepositoryTest {

    @Autowired
    lateinit var attachedStickerRepository: AttachedStickerRepository

    @Autowired
    lateinit var stickerRepository: StickerRepository

    @Autowired
    lateinit var coverRepository: CoverRepository

    @Test
    fun findAttachedStickerWithStickerByAttachedIdAndAttachedType() {
        // given
        val sticker = stickerRepository.save(Sticker(imageUrl = "test.com", name = "라이언 스티커"))
        val cover = coverRepository.save(
            Cover(
                petId = 1,
                targetDate = "2111",
                imageUrl = "test-cover.com",
                color = "blue",
                shapeType = ShapeType.CIRCLE,
                shapeX = 1.0,
                shapeY = 1.0
            )
        )

        attachedStickerRepository.save(AttachedSticker(
            stickerId = sticker.stickerId,
            attachedId = cover.coverId,
            memberId = 1,
            stickerY = 1.0,
            stickerX = 1.0,
            attachedType = AttachedType.COVER
        ))

        attachedStickerRepository.save(AttachedSticker(
            stickerId = sticker.stickerId,
            attachedId = cover.coverId,
            memberId = 1,
            stickerY = 2.0,
            stickerX = 2.0,
            attachedType = AttachedType.COVER
        ))

        // when
        val coverAttachedStickerList = attachedStickerRepository.findAttachedStickerWithStickerByAttachedIdAndAttachedType(cover.coverId, AttachedType.COVER)


        // then
        assertEquals(2, coverAttachedStickerList.count())
    }
}