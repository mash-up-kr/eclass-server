package com.mashup.eclassserver.model.repository

import com.mashup.eclassserver.model.entity.Cover
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface CoverRepository : JpaRepository<Cover, Long> {
    fun findByPetIdAndTargetDate(petId: Long, targetDate: String): Cover
}
