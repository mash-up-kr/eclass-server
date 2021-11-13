package com.mashup.eclassserver.service

import com.mashup.eclassserver.exception.EclassException
import com.mashup.eclassserver.exception.ErrorCode
import com.mashup.eclassserver.model.dto.DiaryRequestDto
import com.mashup.eclassserver.model.dto.DiaryResponseDto
import com.mashup.eclassserver.model.dto.PictureRequestDto
import com.mashup.eclassserver.model.entity.*
import com.mashup.eclassserver.model.repository.AttachedStickerRepository
import com.mashup.eclassserver.model.repository.DiaryPictureRepository
import com.mashup.eclassserver.model.repository.DiaryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class DiaryService(
    private val diaryRepository: DiaryRepository,
    private val diaryPictureRepository: DiaryPictureRepository,
    private val attachedStickerRepository: AttachedStickerRepository
) {
    @Transactional
    fun submitDiary(diaryDto: DiaryRequestDto, member: Member): Diary {
        return saveDiaryWithPictureList(diaryDto, member)
    }

    private fun saveDiaryWithPictureList(diaryDto: DiaryRequestDto, member: Member): Diary {
        val diary = Diary.of(diaryDto, member)
        diaryRepository.save(diary)
        diaryDto.pictureList.map { saveDiaryPictureAndStickers(diary, member, it) }
        return diary
    }

    private fun saveDiaryPictureAndStickers(diary: Diary, member: Member, pictureRequestDto: PictureRequestDto) {
        val diaryPicture = DiaryPicture.of(pictureRequestDto, diary.diaryId)
        diaryPictureRepository.save(diaryPicture)
        val attachedStickerList = pictureRequestDto.attachedStickerDtoList.asSequence()
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
    }

    @Transactional(readOnly = true)
    fun getDiaryList(member: Member): List<DiaryResponseDto> {
        val resultList = diaryRepository.findAllByMember(member)
                .asSequence()
                .map { DiaryResponseDto.of(it) }
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
    fun getDiaryListByDate(member: Member, year: Int, month: Int): List<DiaryResponseDto> {
        val startDate = LocalDate.of(year, month, 1).minusDays(1)
        val endDate = LocalDate.of(year, month, 1).plusMonths(1)
        val resultList = diaryRepository.findAllCreatedAtBetweenAndMember(startDate, endDate, member)
                .map { DiaryResponseDto.of(it) }
        for (diaryDto in resultList) {
            for (picDto in diaryDto.pictureSubmitRequestList) {
                picDto.attachedStickerDtoList.addAll(attachedStickerRepository
                                                             .findAllByAttachedIdAndAttachedType(picDto.diaryPictureId!!, AttachedType.DIARY)
                                                             .map { AttachedSticker.of(it) })
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
    fun findDiaryById(id: Long): DiaryResponseDto {
        val diary = diaryRepository.findBydiaryId(id) ?: throw EclassException(ErrorCode.DIARY_NOT_FOUND)
        val diaryDto = DiaryResponseDto.of(diary)
        for (picDto in diaryDto.pictureSubmitRequestList) {
            picDto.attachedStickerDtoList.addAll(attachedStickerRepository
                                                         .findAllByAttachedIdAndAttachedType(picDto.diaryPictureId!!, AttachedType.DIARY).asSequence()
                                                         .map { AttachedSticker.of(it) }
                                                         .toList())
        }
        return diaryDto
    }
}