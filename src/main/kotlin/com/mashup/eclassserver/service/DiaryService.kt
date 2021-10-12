package com.mashup.eclassserver.service

import com.mashup.eclassserver.exception.EclassException
import com.mashup.eclassserver.exception.ErrorCode
import com.mashup.eclassserver.model.dto.DiarySubmitRequest
import com.mashup.eclassserver.model.entity.*
import com.mashup.eclassserver.model.repository.AttachedStickerRepository
import com.mashup.eclassserver.model.repository.DiaryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DiaryService(
    private val diaryRepository: DiaryRepository,
    private val attachedStickerRepository: AttachedStickerRepository
) {
    @Transactional
    fun submitDiary(diarySubmitRequest: DiarySubmitRequest, member: Member) {
        val diary = saveDiaryWithPictureList(diarySubmitRequest, member)
        saveAttachedStickerList(diarySubmitRequest, diary, member)
    }

    private fun saveDiaryWithPictureList(diarySubmitRequest: DiarySubmitRequest, member: Member): Diary {
        val diary = Diary.of(diarySubmitRequest, member)
        val diaryPictureList = diarySubmitRequest.pictureSubmitRequestList
                .asSequence()
                .map { DiaryPicture.of(it) }
                .toMutableList()
        diary.diaryPictureList.addAll(diaryPictureList)
        return diaryRepository.save(diary)
    }

    private fun saveAttachedStickerList(diarySubmitRequest: DiarySubmitRequest, diary: Diary, member: Member) {
        val attachedStickerList = diarySubmitRequest.pictureSubmitRequestList
                .flatMap {
                    it.attachedStickerDtoList
                            .asSequence()
                            .map {
                                AttachedSticker.of(member.memberId, diary.diaryId, AttachedType.DIARY, it)
                            }
                            .toMutableList()
                }

        attachedStickerRepository.saveAll(attachedStickerList)
    }

    @Transactional
    fun saveBadge(diaryId: Long, badge: Badge) {
        val diary = diaryRepository.findBydiaryId(diaryId) ?: throw EclassException(ErrorCode.DIARY_NOT_FOUND)
        diary.badge = badge
        diaryRepository.save(diary)
    }
}