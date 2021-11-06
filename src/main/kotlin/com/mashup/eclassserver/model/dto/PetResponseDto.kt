package com.mashup.eclassserver.model.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.mashup.eclassserver.model.entity.Pet
import java.time.LocalDateTime

data class PetResponseDto(
    val petId: Long,

    val name: String,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    val birthDate: LocalDateTime,

    val imageUrl: String?
) {
    companion object {
        fun of(pet: Pet) =
                PetResponseDto(
                    petId = pet.petId,
                    name = pet.name,
                    birthDate = pet.birthDate,
                    imageUrl = pet.imageUrl
                )
    }
}