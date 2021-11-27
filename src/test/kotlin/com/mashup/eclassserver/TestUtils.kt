package com.mashup.eclassserver

import com.mashup.eclassserver.model.entity.Member
import com.mashup.eclassserver.supporter.JwtSupporter

object TestUtils {
    fun mockMember(): Member {
        return Member(
            memberId = 356,
            petId = 959,
            nickname = "sample-nickname",
            email = "test@test.com",
            password = "test-password",
            imageUrl = "eclass.com/img/test.png",
            relationship = "sample-relationship"
        )
    }

    fun createToken(member: Member): String {
        return JwtSupporter.createToken(member)
    }
}