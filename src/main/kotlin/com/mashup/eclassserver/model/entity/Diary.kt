package com.mashup.eclassserver.model.entity

import javax.persistence.*

@Entity
data class Diary(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val diaryId: Long = 0,

    val petId: Long,

    @OneToOne(cascade = arrayOf(CascadeType.ALL))
    @JoinColumn(name = "badge_id")
    val badge: Badge,

    @OneToOne
    @JoinColumn(name = "member_id")
    val member: Member,

    @Lob
    var content: String? = null,

    @OneToMany(fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.ALL))
    @JoinColumn(name = "diary_id")
    var diaryPictureList: MutableList<Diary_Picture> = mutableListOf()
) : BaseEntity()