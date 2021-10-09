package com.mashup.eclassserver.model.entity

import com.mashup.eclassserver.model.dto.RegisterReplyRequest
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
) : BaseEntity(){
    companion object{
        fun of(diaryId: Long, request: RegisterReplyRequest, member: Member) =
                Reply(
                        diaryId = diaryId,
                        content = request.content,
                        member = member
                )
    }
}