package com.mashup.eclassserver.model.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Pet(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val petId: Long = 0
) : BaseEntity()