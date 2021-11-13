package com.mashup.eclassserver.supporter

import com.mashup.eclassserver.TestUtils
import com.mashup.eclassserver.infra.ECLogger
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class JwtSupporterTest {

    companion object : ECLogger


    @Test
    @DisplayName("토큰 생성 테스트")
    fun createToken() {
        val token = JwtSupporter.createToken(TestUtils.mockMember())
        assertNotNull(token)
        log.debug("token -> $token")
    }

    @Test
    @DisplayName("토큰 검증 테스트")
    fun verifyToken() {
        val member = TestUtils.mockMember()
        val token = JwtSupporter.createToken(member)
        val loginInfoDto = JwtSupporter.verifyToken(token)
        assertEquals(member.memberId, loginInfoDto.memberId)
        assertEquals(member.petId, loginInfoDto.petId)
    }
}