package com.mashup.eclassserver.model.dto

data class PictureRequestDto(
    val imageUrl: String,
    val isThumbnail: Boolean,
    var attachedStickerDtoList: MutableList<AttachedStickerDto>
)
