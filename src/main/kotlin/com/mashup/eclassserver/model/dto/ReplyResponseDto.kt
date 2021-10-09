package com.mashup.eclassserver.model.dto

import com.mashup.eclassserver.model.entity.Member
import com.mashup.eclassserver.model.entity.Reply

data class ReplyResponseDto(
    val member: Member,
    var content: String? = null
) {
    companion object {
        fun of(reply: Reply) = ReplyResponseDto(reply.member, reply.content)
    }
}

