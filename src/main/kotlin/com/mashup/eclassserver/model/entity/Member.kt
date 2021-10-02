package com.mashup.eclassserver.model.entity

import javax.persistence.*

@Entity
data class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val memberId: Long = 0,

    @Column(name = "pet_id")
    val petId: Long = 0,

    val nickname: String
) : BaseEntity()