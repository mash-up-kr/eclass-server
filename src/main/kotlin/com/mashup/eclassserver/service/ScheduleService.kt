package com.mashup.eclassserver.service

import com.mashup.eclassserver.model.dto.ScheduleRequestDto
import com.mashup.eclassserver.model.entity.Member
import com.mashup.eclassserver.model.entity.Repeat
import com.mashup.eclassserver.model.entity.Schedule
import com.mashup.eclassserver.model.repository.RepeatRepository
import com.mashup.eclassserver.model.repository.ScheduleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ScheduleService(
    private val repeatRepository: RepeatRepository,
    private val scheduleRepository: ScheduleRepository
) {
    @Transactional
    fun saveSchedule(member: Member, scheduleRequestDto: ScheduleRequestDto) {
        scheduleRequestDto.repeatType?.let {
            val saveRepeat = Repeat.of(scheduleRequestDto, member)
            repeatRepository.save(saveRepeat)
        } ?: {
            val saveSchedule = Schedule.of(scheduleRequestDto, member)
            scheduleRepository.save(saveSchedule)
        }()
    }
}