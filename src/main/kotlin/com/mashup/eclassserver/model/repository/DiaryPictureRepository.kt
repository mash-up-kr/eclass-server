package com.mashup.eclassserver.model.repository

import com.mashup.eclassserver.model.entity.DiaryPicture
import org.springframework.data.jpa.repository.JpaRepository

interface DiaryPictureRepository : JpaRepository<DiaryPicture, Long> {
}