package com.mashup.eclassserver.model.dto

data class PictureSubmitRequest(
    val diaryPictureId: Long?,
    val imageUrl: String,
    val isThumbnail: Boolean,
    var attachedStickerDtoList: MutableList<AttachedStickerDto>
)