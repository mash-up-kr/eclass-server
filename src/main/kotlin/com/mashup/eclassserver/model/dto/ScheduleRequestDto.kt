package com.mashup.eclassserver.model.dto

import com.mashup.eclassserver.model.entity.RepeatType
import java.time.LocalDateTime

data class ScheduleRequestDto(
    val name: String,

    val location: String,

    val color: String,

    val scheduledAt: LocalDateTime,

    val repeatType: RepeatType?
)