package com.mashup.eclassserver.controller

import com.mashup.eclassserver.model.dto.BadgeResponseDto
import com.mashup.eclassserver.service.BadgeService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController("/api/v1/badge")
class BadgeController(
    private val badgeService: BadgeService
) {
    @GetMapping("/list")
    fun getBadgeList() : ResponseEntity<List<BadgeResponseDto>> {
        return ResponseEntity.ok(badgeService.getBadgeList())
    }
}