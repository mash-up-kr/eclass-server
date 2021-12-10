package com.mashup.eclassserver.controller

import com.mashup.eclassserver.model.dto.StickerDto
import com.mashup.eclassserver.service.StickerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/sticker")
class StickerController(
    private val stickerService: StickerService
) {
    @GetMapping
    fun getStickers(): ResponseEntity<List<StickerDto>> {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(stickerService.getStickers())
    }
}