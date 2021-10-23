package com.mashup.eclassserver.controller

import com.mashup.eclassserver.model.dto.BadgePostDto
import com.mashup.eclassserver.model.dto.BadgeResponse
import com.mashup.eclassserver.service.BadgeService
import com.mashup.eclassserver.service.DiaryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/badge")
@RestController
class BadgeController(
    private val badgeService: BadgeService,
    private val diaryService: DiaryService
) {
    @GetMapping
    fun getBadgeList(): ResponseEntity<BadgeResponse> {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(badgeService.getBadgeList())
    }

    @PostMapping
    fun postBadge(@RequestBody badgePostDto: BadgePostDto): ResponseEntity<Unit> {
        val badge = badgeService.findBadgeById(badgePostDto.badgeId)
        diaryService.saveBadge(badgePostDto.diaryId, badge)
        return ResponseEntity
                .status(HttpStatus.OK).build()
    }
}