package com.mashup.eclassserver.model.entity

import javax.persistence.*

@Entity
data class AttachedSticker(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val attachedStickerId: Long = 0,
    val stickerId: Long,
    val attachedId: Long,
    val memberId: Long,
    @Column(name ="sticker_x")
    val stickerX: Double,
    @Column(name ="sticker_y")
    val stickerY: Double,
    @Enumerated(EnumType.STRING)
    val attachedType: AttachedType
) : BaseEntity()

enum class AttachedType {
    COVER,
    DIARY
}
