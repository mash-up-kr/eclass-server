package com.mashup.eclassserver.controller

import com.mashup.eclassserver.model.dto.CoverData
import com.mashup.eclassserver.model.dto.CoverResponseDto
import com.mashup.eclassserver.service.CoverService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/cover")
class CoverController(private val coverService: CoverService) {

    @GetMapping("/{targetDate}")
    fun home(@PathVariable @DateTimeFormat(pattern = "yyMM") targetDate: String): CoverResponseDto {
        return coverService.homeByMonth(1L, targetDate)
    }

    @PostMapping
    fun register(@RequestPart coverData: CoverData, @RequestParam imageFile: MultipartFile) {
        coverService.register(1L, 1L, coverData, imageFile)
    }
}