package com.mashup.eclassserver.model.entity

import com.mashup.eclassserver.model.dto.PetPostDto
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Pet(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val petId: Long = 0,

    val name: String,

    val birthDate: LocalDateTime,

    var imageUrl: String?
) : BaseEntity() {
    companion object {
        fun of(request: PetPostDto) =
                Pet(
                    name = request.name,
                    imageUrl = null,
                    birthDate = request.birthDate
                )
    }
}