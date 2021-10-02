package com.mashup.eclassserver.model.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class AttachedSticker(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val attachedStickerId: Long = 0,
    val imageUrl: String,
    val name: String
) : BaseEntity()
