package com.mashup.eclassserver.model.dto

import com.mashup.eclassserver.model.entity.Sticker

data class StickerDto(
    val stickerId: Long,
    val name: String,
    val imageUrl: String
) {
    companion object {
        fun of(sticker: Sticker) = StickerDto(sticker.stickerId, sticker.name, sticker.imageUrl)
    }
}
