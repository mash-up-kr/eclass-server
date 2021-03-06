package com.mashup.eclassserver.model.repository

import com.mashup.eclassserver.model.entity.Diary
import com.mashup.eclassserver.model.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface DiaryRepository : JpaRepository<Diary, Long> {
    fun findBydiaryId(diaryId: Long): Diary?
    fun findAllByMember(member: Member): List<Diary>
    fun findAllByCreatedAtBetweenAndMember(start: LocalDateTime, end: LocalDateTime, member: Member): List<Diary>
}