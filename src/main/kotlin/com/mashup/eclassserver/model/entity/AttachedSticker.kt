package com.mashup.eclassserver.model.entity

import com.mashup.eclassserver.model.dto.AttachedStickerSubmitRequest
import javax.persistence.*

@Entity
data class AttachedSticker(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attached_sticker_id")
    val attachedStickerId: Long = 0,

    val stickerId: Long,

    val memberId: Long,

    @Column(name = "sticker_x")
    val stickerX: Double,

    @Column(name = "sticker_y")
    val stickerY: Double,

    @Enumerated(EnumType.STRING)
    val attachedType: AttachedType,
) : BaseEntity() {
    companion object {
        fun of(request: AttachedStickerSubmitRequest, memberId: Long) =
                AttachedSticker(stickerId = request.stickerId,
                                memberId = memberId,
                                stickerX = request.stickerX,
                                stickerY = request.stickerY,
                                attachedType = AttachedType.DIARY
                )
    }
}

enum class AttachedType {
    COVER,
    DIARY
}