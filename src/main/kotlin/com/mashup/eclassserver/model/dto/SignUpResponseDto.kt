package com.mashup.eclassserver.model.dto

import com.mashup.eclassserver.model.entity.Member

data class SignUpResponseDto(
    val memberId: Long,
    val email: String
) {
    companion object {
        fun of(member: Member) = SignUpResponseDto(member.memberId, member.email)
    }
}