package com.mashup.eclassserver.model.repository

import com.mashup.eclassserver.model.entity.Repeat
import org.springframework.data.jpa.repository.JpaRepository

interface RepeatRepository : JpaRepository<Repeat, Long> {
}