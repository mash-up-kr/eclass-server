package com.mashup.eclassserver.model.dto

import com.querydsl.core.annotations.QueryProjection

data class CoverResponseDto(
    val imageUrl: String,
    val attachedStickerList: List<CoverAttachedSticker>
)

data class CoverAttachedSticker @QueryProjection constructor(
    val imageUrl: String,
    val stickerX: Double,
    val stickerY: Double
)