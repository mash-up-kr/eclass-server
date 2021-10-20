package com.mashup.eclassserver.model.dto

import com.mashup.eclassserver.model.entity.Member
import com.mashup.eclassserver.model.entity.Reply

data class ReplyResponseDto(
    val memberName: String,
    var content: String? = null
) {
    companion object {
        fun of(reply: Reply) = ReplyResponseDto(reply.member.nickname, reply.content)
    }
}

