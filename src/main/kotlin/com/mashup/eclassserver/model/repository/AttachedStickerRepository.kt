package com.mashup.eclassserver.model.repository

import com.mashup.eclassserver.model.entity.AttachedSticker
import com.mashup.eclassserver.model.entity.AttachedType
import org.springframework.data.jpa.repository.JpaRepository

interface AttachedStickerRepository : JpaRepository<AttachedSticker, Long> {
    fun findAllByAttachedIdAndAttachedType(attachedId: Long, attachedType: AttachedType): List<AttachedSticker>
}