package com.mashup.eclassserver.model.repository

import com.mashup.eclassserver.model.dto.CoverAttachedSticker
import com.mashup.eclassserver.model.dto.QCoverAttachedSticker
import com.mashup.eclassserver.model.entity.AttachedSticker
import com.mashup.eclassserver.model.entity.AttachedType
import com.mashup.eclassserver.model.entity.QAttachedSticker.attachedSticker
import com.mashup.eclassserver.model.entity.QSticker.sticker
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.JpaRepository

interface AttachedStickerRepository : JpaRepository<AttachedSticker, Long>, AttachedStickerRepositoryCustom {
    fun findAllByAttachedIdAndAttachedType(attachedId: Long, attachedType: AttachedType): List<AttachedSticker>
}

interface  AttachedStickerRepositoryCustom {
    fun findAttachedStickerWithStickerByAttachedIdAndAttachedType(attachedId: Long, attachedType: AttachedType): List<CoverAttachedSticker>
}

class AttachedStickerRepositoryCustomImpl(private val jpaQueryFactory: JPAQueryFactory) : AttachedStickerRepositoryCustom {
    override fun findAttachedStickerWithStickerByAttachedIdAndAttachedType(attachedId: Long, attachedType: AttachedType): List<CoverAttachedSticker> {
        return jpaQueryFactory.select(
            QCoverAttachedSticker(
                sticker.imageUrl,
                attachedSticker.stickerX,
                attachedSticker.stickerY
            )
        ).from(attachedSticker)
            .innerJoin(sticker).on(attachedSticker.stickerId.eq(sticker.stickerId))
            .where(attachedSticker.attachedId.eq(attachedId).and(attachedSticker.attachedType.eq(attachedType)))
            .fetch()
    }
}