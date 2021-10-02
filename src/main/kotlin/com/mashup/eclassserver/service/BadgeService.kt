package com.mashup.eclassserver.service

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
    fun getBadgeList() : List<BadgeResponseDto> {
        return badgeRepository.findAll()
                .map { badge -> BadgeResponseDto.of(badge) }
                .toList()
    }
}