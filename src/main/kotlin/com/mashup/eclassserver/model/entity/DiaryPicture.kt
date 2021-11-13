package com.mashup.eclassserver.model.entity

import com.mashup.eclassserver.model.dto.PictureRequestDto
import com.mashup.eclassserver.model.dto.PictureSubmitRequest
import javax.persistence.*

@Entity
data class DiaryPicture(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val diaryPictureId: Long = 0,

    val imageUrl: String,

    val isThumbnail: Boolean,

    @Column(name = "diary_id")
    val diaryId: Long
) : BaseEntity() {
    companion object {
        fun of(request: PictureSubmitRequest, diaryId: Long) =
                DiaryPicture(
                    imageUrl = request.imageUrl,
                    isThumbnail = request.isThumbnail,
                    diaryId = diaryId
                )

        fun of(request: PictureRequestDto, diaryId: Long) =
                DiaryPicture(
                    imageUrl = request.imageUrl,
                    isThumbnail = request.isThumbnail,
                    diaryId = diaryId
                )

        fun of(diaryPicture: DiaryPicture) =
                PictureSubmitRequest(
                    diaryPictureId = diaryPicture.diaryPictureId,
                    imageUrl = diaryPicture.imageUrl,
                    isThumbnail = diaryPicture.isThumbnail,
                    attachedStickerDtoList = mutableListOf()
                )
    }
}