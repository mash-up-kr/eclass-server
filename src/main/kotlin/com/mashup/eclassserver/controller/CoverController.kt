package com.mashup.eclassserver.controller

import com.mashup.eclassserver.model.dto.CoverData
import com.mashup.eclassserver.service.CoverService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/cover")
class CoverController(
    private val coverService: CoverService
) {

    @PostMapping
    fun register(@RequestPart coverData: CoverData, @RequestParam imageFile: MultipartFile) {
        coverService.register(1L, 1L, coverData, imageFile)
    }
}