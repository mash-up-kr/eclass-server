package com.mashup.eclassserver.model.repository

import com.mashup.eclassserver.model.entity.Sticker
import org.springframework.data.jpa.repository.JpaRepository

interface StickerRepository : JpaRepository<Long, Sticker> {
}