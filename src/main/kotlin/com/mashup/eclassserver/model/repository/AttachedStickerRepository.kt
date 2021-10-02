package com.mashup.eclassserver.model.repository

import com.mashup.eclassserver.model.entity.AttachedSticker
import org.springframework.data.jpa.repository.JpaRepository

interface AttachedStickerRepository : JpaRepository<AttachedSticker, Long> {
}