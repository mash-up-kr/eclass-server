package com.mashup.eclassserver.model.entity

import com.mashup.eclassserver.model.dto.PictureSubmitRequest
import javax.persistence.*

@Entity
data class DiaryPicture(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val diaryPictureId: Long = 0,

    val imageUrl: String,

    val isThumbnail: Boolean,

    @OneToMany(fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.ALL))
    @JoinColumn(name = "attached_id")
    var attachedStickerList: MutableList<AttachedSticker>
) : BaseEntity() {
    companion object {
        fun of(request: PictureSubmitRequest, memberId: Long) =
                DiaryPicture(
                    imageUrl = request.imageUrl,
                    isThumbnail = request.isThumbnail,
                    attachedStickerList =
                    request.attachedStickerSubmitRequestList.asSequence()
                            .map { AttachedSticker.of(it, memberId) }
                            .toMutableList()
                )
    }
}