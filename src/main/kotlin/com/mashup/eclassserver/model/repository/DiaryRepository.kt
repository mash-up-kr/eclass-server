package com.mashup.eclassserver.model.repository

import com.mashup.eclassserver.model.entity.Diary
import org.springframework.data.jpa.repository.JpaRepository

interface DiaryRepository : JpaRepository<Diary, Long> {
    fun findBydiaryId(diaryId: Long): Diary?
}