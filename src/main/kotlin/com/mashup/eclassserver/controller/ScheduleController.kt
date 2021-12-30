package com.mashup.eclassserver.controller

import com.mashup.eclassserver.model.dto.ScheduleRequestDto
import com.mashup.eclassserver.model.repository.MemberRepository
import com.mashup.eclassserver.service.ScheduleService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/schedule")
class ScheduleController(
    private val memberRepository: MemberRepository,
    private val scheduleService: ScheduleService
) {
    @PostMapping
    fun saveSchedule(@RequestBody scheduleRequestDto: ScheduleRequestDto): ResponseEntity<Unit> {
        val member = memberRepository.findById(1).get()

        scheduleService.saveSchedule(member, scheduleRequestDto)
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null)
    }
}