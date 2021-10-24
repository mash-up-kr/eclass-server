package com.mashup.eclassserver.service

import com.mashup.eclassserver.exception.EclassException
import com.mashup.eclassserver.exception.ErrorCode
import com.mashup.eclassserver.model.dto.DiaryDto
import com.mashup.eclassserver.model.dto.PictureSubmitRequest
import com.mashup.eclassserver.model.entity.*
import com.mashup.eclassserver.model.repository.AttachedStickerRepository
import com.mashup.eclassserver.model.repository.DiaryPictureRepository
import com.mashup.eclassserver.model.repository.DiaryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DiaryService(
    private val diaryRepository: DiaryRepository,
    private val diaryPictureRepository: DiaryPictureRepository,
    private val attachedStickerRepository: AttachedStickerRepository
) {
    @Transactional
    fun submitDiary(diaryDto: DiaryDto, member: Member) {
        saveDiaryWithPictureList(diaryDto, member)
    }

    private fun saveDiaryWithPictureList(diaryDto: DiaryDto, member: Member) {
        val diary = Diary.of(diaryDto, member)
        diaryRepository.save(diary)
        diaryDto.pictureSubmitRequestList.map { saveDiaryPictureAndStickers(diary, member, it) }
    }

    private fun saveDiaryPictureAndStickers(diary: Diary, member: Member, pictureSubmitRequest: PictureSubmitRequest) {
        val diaryPicture = DiaryPicture.of(pictureSubmitRequest, diary.diaryId)
        diaryPictureRepository.save(diaryPicture)
        val attachedStickerList = pictureSubmitRequest.attachedStickerDtoList.asSequence()
                .map {
                    AttachedSticker.of(member.memberId, diaryPicture.diaryPictureId, AttachedType.DIARY, it)
                }
                .toList()
        attachedStickerRepository.saveAll(attachedStickerList)
    }

    @Transactional
    fun saveBadge(diaryId: Long, badge: Badge) {
        val diary = diaryRepository.findBydiaryId(diaryId) ?: throw EclassException(ErrorCode.DIARY_NOT_FOUND)
        diary.badge = badge
        diaryRepository.save(diary)

        @Transactional(readOnly = true)
        fun getDiaryList(member: Member): List<DiaryDto> {
            val resultList = diaryRepository.findAllByMember(member)
                    .asSequence()
                    .map { Diary.of(it) }
                    .toList()
            for (diaryDto in resultList) {
                for (picDto in diaryDto.pictureSubmitRequestList) {
                    picDto.attachedStickerDtoList.addAll(attachedStickerRepository
                                                                 .findAllByAttachedIdAndAttachedType(picDto.diaryPictureId!!, AttachedType.DIARY).asSequence()
                                                                 .map { AttachedSticker.of(it) }
                                                                 .toList())
                }
            }
            return resultList
        }
    }

    @Transactional(readOnly = true)
    fun getDiaryList(member: Member): List<DiaryDto> {
        val resultList = diaryRepository.findAllByMember(member)
                .asSequence()
                .map { Diary.of(it) }
                .toList()
        for (diaryDto in resultList) {
            for (picDto in diaryDto.pictureSubmitRequestList) {
                picDto.attachedStickerDtoList.addAll(attachedStickerRepository
                                                             .findAllByAttachedIdAndAttachedType(picDto.diaryPictureId!!, AttachedType.DIARY).asSequence()
                                                             .map { AttachedSticker.of(it) }
                                                             .toList())
            }
        }
        return resultList
    }

    @Transactional(readOnly = true)
    fun getDiaryIdList(member: Member): List<Long> {
        return diaryRepository.findAllByMember(member)
                .asSequence()
                .map { it.diaryId }
                .toList()
    }

    @Transactional(readOnly = true)
    fun findDiaryById(id: Long): DiaryDto {
        val diary = diaryRepository.findBydiaryId(id) ?: throw EclassException(ErrorCode.DIARY_NOT_FOUND)
        return Diary.of(diary)
    }
}