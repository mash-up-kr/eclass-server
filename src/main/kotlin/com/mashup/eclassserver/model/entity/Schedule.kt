package com.mashup.eclassserver.model.entity

import com.mashup.eclassserver.model.dto.ScheduleRequestDto
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Schedule(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val scheduleId: Long = 0,

    val name: String,

    val location: String,

    val color: String,

    val scheduledAt: LocalDateTime,

    val petId: Long
) {
    companion object {
        fun of(scheduleRequestDto: ScheduleRequestDto, member: Member): Schedule =
                Schedule(
                    name = scheduleRequestDto.name,
                    location = scheduleRequestDto.location,
                    color = scheduleRequestDto.color,
                    scheduledAt = scheduleRequestDto.scheduledAt,
                    petId = member.petId
                )
    }
}