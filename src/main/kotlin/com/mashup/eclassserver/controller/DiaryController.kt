package com.mashup.eclassserver.controller

import com.mashup.eclassserver.model.dto.DiaryDto
import com.mashup.eclassserver.model.repository.MemberRepository
import com.mashup.eclassserver.service.DiaryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/diary")
class DiaryController(
    private val diaryService: DiaryService,
    private val memberRepository: MemberRepository
) {
    @PostMapping
    fun submitDiary(@RequestBody diaryDto: DiaryDto): ResponseEntity<*> {
        val member = memberRepository.findById(1).get()

        diaryService.submitDiary(diaryDto, member)
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null)
    }

    @GetMapping
    fun getDiary(): ResponseEntity<*> {
        val member = memberRepository.findById(1).get()

        val resultList = diaryService.getDiaryList(member)
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(resultList)
    }
}