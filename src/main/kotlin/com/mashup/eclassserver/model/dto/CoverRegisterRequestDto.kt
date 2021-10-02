package com.mashup.eclassserver.model.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.mashup.eclassserver.model.entity.ShapeType
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate

data class CoverRegisterRequestDto(
    val imageFile: MultipartFile,
    val coverData: CoverData
)

data class CoverData(
    val attachedStickerList: List<AttachedStickerDto>,
    val color: String,
    val shapeType: ShapeType,
    val shapeX: Double,
    val shapeY: Double,

    @JsonFormat( pattern = "yyyy-MM-dd")
    val targetDate: LocalDate
)

data class AttachedStickerDto(
    val stickerId: Long,
    val stickerX: Double,
    val stickerY: Double
)
