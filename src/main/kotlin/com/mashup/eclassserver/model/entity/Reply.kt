package com.mashup.eclassserver.model.entity

import javax.persistence.*

@Entity
data class Reply(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val replyId: Long = 0,

    val content: String? = null,

    @OneToOne
    @JoinColumn(name = "member_id")
    val member: Member
) : BaseEntity()