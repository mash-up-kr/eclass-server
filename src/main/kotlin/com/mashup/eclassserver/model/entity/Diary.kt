package com.mashup.eclassserver.model.entity

import com.mashup.eclassserver.model.dto.DiaryDto
import com.mashup.eclassserver.model.dto.DiaryRequestDto
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
    var diaryPictureList: MutableList<DiaryPicture> = mutableListOf(),

    @OneToMany(fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.ALL), orphanRemoval = true)
    @JoinColumn(name = "diary_id")
    var replyList: MutableList<Reply> = mutableListOf()
) : BaseEntity() {
    companion object {
        fun of(request: DiaryDto, member: Member) =
                Diary(
                    petId = member.petId,
                    member = member,
                    content = request.content
                )

        fun of(request: DiaryRequestDto, member: Member) =
                Diary(
                    petId = member.petId,
                    member = member,
                    content = request.content
                )
    }
}