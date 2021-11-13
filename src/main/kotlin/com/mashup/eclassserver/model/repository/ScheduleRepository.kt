package com.mashup.eclassserver.model.repository

import com.mashup.eclassserver.model.entity.Schedule
import org.springframework.data.jpa.repository.JpaRepository

interface ScheduleRepository : JpaRepository<Schedule, Long> {
}