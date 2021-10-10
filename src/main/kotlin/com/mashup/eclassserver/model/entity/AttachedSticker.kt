package com.mashup.eclassserver.model.entity

import com.mashup.eclassserver.model.dto.AttachedStickerDto
import javax.persistence.*

@Entity
data class AttachedSticker(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val attachedStickerId: Long = 0,
    val stickerId: Long,
    val attachedId: Long,
    val memberId: Long,
    @Column(name = "sticker_x")
    val stickerX: Double,
    @Column(name = "sticker_y")
    val stickerY: Double,
    @Enumerated(EnumType.STRING)
    val attachedType: AttachedType
) : BaseEntity() {
    companion object {
        fun of(memberId: Long, attachedId: Long, type: AttachedType, attachedStickerDto: AttachedStickerDto): AttachedSticker {
            return AttachedSticker(
                memberId = memberId,
                stickerId = attachedStickerDto.stickerId,
                attachedId = attachedId,
                stickerX = attachedStickerDto.stickerX,
                stickerY = attachedStickerDto.stickerY,
                attachedType = type
            )
        }
    }
}

enum class AttachedType {
    COVER,
    DIARY
}