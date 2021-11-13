package com.mashup.eclassserver.model.dto

data class DiaryRequestDto(
    val content: String?,
    val pictureList: List<PictureRequestDto>,
    val badgeId: Long?
)