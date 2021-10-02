package com.mashup.eclassserver.model.repository

import com.mashup.eclassserver.model.entity.Diary_Picture
import org.springframework.data.jpa.repository.JpaRepository

interface DiaryPictureRepository : JpaRepository<Diary_Picture, Long> {
}