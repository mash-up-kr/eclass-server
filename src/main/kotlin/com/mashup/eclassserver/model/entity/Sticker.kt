package com.mashup.eclassserver.model.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Sticker(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val stickerId: Long = 0,

    val imageUrl: String? = null,

    val name: String? = null
) : BaseEntity()