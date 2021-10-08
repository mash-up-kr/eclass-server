package com.mashup.eclassserver.model.dto

data class DiarySubmitRequest(
    val content: String,
    val pictureSubmitRequestList: List<PictureSubmitRequest>
)