package com.mashup.eclassserver.controller

import com.mashup.eclassserver.model.dto.DiarySubmitRequest
import com.mashup.eclassserver.model.repository.MemberRepository
import com.mashup.eclassserver.service.DiaryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping(value = ["/api/v1/diary"])
@RestController
class DiaryController(
    private val diaryService: DiaryService,
    private val memberRepository: MemberRepository
) {
    @PostMapping("/submit")
    fun submitDiary(@RequestBody diarySubmitRequest: DiarySubmitRequest): ResponseEntity.BodyBuilder {
        val member = memberRepository.findById(1).get() //dummy data

        diaryService.submitDiary(diarySubmitRequest, member)
        return ResponseEntity.status(HttpStatus.OK)
    }
}