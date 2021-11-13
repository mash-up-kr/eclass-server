package com.mashup.eclassserver.model.entity

import javax.persistence.*

@Entity
data class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val memberId: Long = 0,

    val petId: Long = 0,

    val nickname: String,

    val email: String,

    val password: String,

    var imageUrl: String? = null,

    val relationship: String = ""
)