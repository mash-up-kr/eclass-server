package com.mashup.eclassserver.service

import com.mashup.eclassserver.model.dto.StickerDto
import com.mashup.eclassserver.model.repository.StickerRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StickerService(
    private val stickerRepository: StickerRepository
) {
    @Transactional(readOnly = true)
    fun getStickers(): List<StickerDto> {
        return stickerRepository.findAll().map { StickerDto.of(it) }.toList()
    }
}