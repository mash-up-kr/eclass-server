package com.mashup.eclassserver.model.entity

import com.mashup.eclassserver.model.dto.PictureSubmitRequest
import javax.persistence.*

@Entity
data class DiaryPicture(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val diaryPictureId: Long = 0,

    val imageUrl: String,

    val isThumbnail: Boolean
) : BaseEntity() {
    companion object {
        fun of(request: PictureSubmitRequest) =
                DiaryPicture(
                    imageUrl = request.imageUrl,
                    isThumbnail = request.isThumbnail
                )
    }
}