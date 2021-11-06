package com.mashup.eclassserver.model.dto

data class DiaryDto(
    val content: String?,
    val pictureSubmitRequestList: List<PictureSubmitRequest>,
    val badgeId: Long?
)