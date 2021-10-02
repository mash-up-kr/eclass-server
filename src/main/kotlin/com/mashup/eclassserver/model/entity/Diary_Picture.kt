package com.mashup.eclassserver.model.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Diary_Picture(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val diaryPictureId: Long = 0,

    val imageUrl: String? = null,

    val isThumbnail: Boolean? = false
) : BaseEntity()