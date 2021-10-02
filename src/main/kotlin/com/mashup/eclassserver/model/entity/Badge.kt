package com.mashup.eclassserver.model.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Badge(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val BadgeId: Long = 0,

    val name: String,

    val imageUrl: String
) : BaseEntity()