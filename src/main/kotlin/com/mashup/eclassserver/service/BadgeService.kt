package com.mashup.eclassserver.service

import com.mashup.eclassserver.model.dto.BadgeResponse
import com.mashup.eclassserver.model.dto.BadgeResponseDto
import com.mashup.eclassserver.model.repository.BadgeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class BadgeService(
    private val badgeRepository: BadgeRepository
) {
    @Transactional(readOnly = true)
    fun getBadgeList(): BadgeResponse {
        return BadgeResponse(badgeRepository.findAll()
                .map { badge -> BadgeResponseDto.of(badge) }
                .toList())
    }
}