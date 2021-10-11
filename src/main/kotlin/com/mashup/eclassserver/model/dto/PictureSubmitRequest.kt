package com.mashup.eclassserver.model.dto

data class PictureSubmitRequest(
    val imageUrl: String,
    val isThumbnail: Boolean,
    val attachedStickerDtoList: List<AttachedStickerDto>
)