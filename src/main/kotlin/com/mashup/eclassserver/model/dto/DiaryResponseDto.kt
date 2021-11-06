package com.mashup.eclassserver.model.dto

import com.mashup.eclassserver.model.entity.Diary
import com.mashup.eclassserver.model.entity.DiaryPicture

data class DiaryResponseDto(
    val diaryId: Long?,
    val content: String?,
    val pictureSubmitRequestList: List<PictureSubmitRequest>,
    val badgeResponseDto: BadgeResponseDto?
) {
    companion object {
        fun of(diary: Diary) =
                DiaryResponseDto(
                    diaryId = diary.diaryId,
                    content = diary.content,
                    pictureSubmitRequestList = diary.diaryPictureList
                            .asSequence()
                            .map { DiaryPicture.of(it) }
                            .toList(),
                    badgeResponseDto = diary.badge?.let { BadgeResponseDto.of(it) }
                )
    }
}