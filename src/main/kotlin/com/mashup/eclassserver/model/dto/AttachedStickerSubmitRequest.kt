package com.mashup.eclassserver.model.dto

data class AttachedStickerSubmitRequest(
    val stickerId: Long,
    val stickerX: Double,
    val stickerY: Double
)