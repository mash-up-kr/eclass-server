package com.mashup.eclassserver.model.repository

import com.mashup.eclassserver.model.entity.Cover
import org.springframework.data.jpa.repository.JpaRepository

interface CoverRepository : JpaRepository<Cover, Long> {
}