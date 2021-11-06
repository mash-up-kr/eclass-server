package com.mashup.eclassserver.model.dto

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

data class PetEditDto(
    val name: String?,

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    val birthDate: LocalDateTime?,
)