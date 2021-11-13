package com.mashup.eclassserver.repository

import com.mashup.eclassserver.model.entity.Sticker
import com.mashup.eclassserver.model.repository.StickerRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@RepositoryTest
class StickerRepositoryTest {

    @Autowired
    lateinit var stickerRepository: StickerRepository

    @Test
    fun findByStickerIdIn() {
        // given
        val stickerIdList = mutableListOf<Long>().apply {
            repeat(3) {
                stickerRepository.save(Sticker(imageUrl = "image-url", name = "test-sticker")).also { this.add(it.stickerId) }
            }
        }

        stickerIdList.add(900)
        stickerIdList.add(9000)

        // when
        val stickerList = stickerRepository.findByStickerIdIn(stickerIdList)

        // then
        assertEquals(3, stickerList.count())
    }

}