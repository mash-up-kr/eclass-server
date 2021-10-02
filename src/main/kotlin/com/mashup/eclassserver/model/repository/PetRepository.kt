package com.mashup.eclassserver.model.repository

import com.mashup.eclassserver.model.entity.Pet
import org.springframework.data.jpa.repository.JpaRepository

interface PetRepository : JpaRepository<Pet, Long> {
}