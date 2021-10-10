package com.mashup.eclassserver.service

import com.mashup.eclassserver.model.dto.DiarySubmitRequest
import com.mashup.eclassserver.model.entity.*
import com.mashup.eclassserver.model.repository.AttachedStickerRepository
import com.mashup.eclassserver.model.repository.DiaryRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

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
}