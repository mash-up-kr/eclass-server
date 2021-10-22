package com.mashup.eclassserver.model.entity

import com.mashup.eclassserver.model.dto.DiaryDto
import javax.persistence.*

@Entity
data class Diary(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val diaryId: Long = 0,

    val petId: Long,

    @ManyToOne
    @JoinColumn(name = "badge_id")
    var badge: Badge? = null,

    @ManyToOne
    @JoinColumn(name = "member_id")
    val member: Member,

    @Lob
    var content: String? = null,

    @OneToMany(fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.ALL))
    @JoinColumn(name = "diary_id")
    var diaryPictureList: MutableList<DiaryPicture> = mutableListOf()
) : BaseEntity() {
    companion object {
        fun of(request: DiaryDto, member: Member) =
                Diary(
                    petId = member.petId,
                    member = member,
                    content = request.content
                )

        fun of(diary: Diary) =
                DiaryDto(
                    content = diary.content,
                    pictureSubmitRequestList = diary.diaryPictureList
                            .asSequence()
                            .map { DiaryPicture.of(it) }
                            .toList()
                )
    }
}