package com.mashup.eclassserver.service

import com.mashup.eclassserver.model.dto.DiarySubmitRequest
import com.mashup.eclassserver.model.entity.Diary
import com.mashup.eclassserver.model.entity.DiaryPicture
import com.mashup.eclassserver.model.entity.Member
import com.mashup.eclassserver.model.repository.DiaryRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class DiaryService(private val diaryRepository: DiaryRepository) {
    @Transactional
    fun submitDiary(diarySubmitRequest: DiarySubmitRequest, member: Member) {
        val diary = diaryRepository.save(Diary.of(diarySubmitRequest, member))
        diary.diaryPictureList = diarySubmitRequest.pictureSubmitRequestList.asSequence()
                .map { DiaryPicture.of(it, member.memberId, diary.diaryId) }
                .toMutableList()
        diaryRepository.save(diary)
    }
}