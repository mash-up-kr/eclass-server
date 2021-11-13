package com.mashup.eclassserver.model.repository

import com.mashup.eclassserver.model.entity.Diary
import com.mashup.eclassserver.model.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface DiaryRepository : JpaRepository<Diary, Long> {
    fun findBydiaryId(diaryId: Long): Diary?
    fun findAllByMember(member: Member): List<Diary>
    fun findAllCreatedAtBetweenAndMember(start: LocalDate, end: LocalDate, member: Member): List<Diary>
}