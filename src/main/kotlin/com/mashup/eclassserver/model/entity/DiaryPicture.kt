package com.mashup.eclassserver.model.entity

import javax.persistence.*

@Entity
data class DiaryPicture(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val diaryPictureId: Long = 0,

    @Column(name = "diary_id")
    val diaryId: Long = 0,

    val imageUrl: String,

    val isThumbnail: Boolean
) : BaseEntity()