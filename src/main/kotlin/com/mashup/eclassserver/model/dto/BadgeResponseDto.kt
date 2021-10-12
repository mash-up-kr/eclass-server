package com.mashup.eclassserver.model.dto

import com.mashup.eclassserver.model.entity.Badge

data class BadgeResponseDto(
    val badgeId: Long,
    val name: String,
    val imageUrl: String
) {
    companion object {
        fun of(badge: Badge) = BadgeResponseDto(badge.badgeId, badge.name, badge.imageUrl)
    }
}
