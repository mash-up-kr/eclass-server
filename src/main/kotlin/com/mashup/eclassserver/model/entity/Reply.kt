package com.mashup.eclassserver.model.entity

import javax.persistence.*

@Entity
data class Reply(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val replyId: Long = 0,

    @Column(name = "diary_id")
    val diaryId: Long = 0,

    @Lob
    var content: String? = null,

    @OneToOne
    @JoinColumn(name = "member_id")
    val member: Member
) : BaseEntity()