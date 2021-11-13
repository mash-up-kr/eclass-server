package com.mashup.eclassserver.supporter

import com.mashup.eclassserver.constants.DEFAULT_OBJECT_MAPPER
import com.mashup.eclassserver.exception.EclassException
import com.mashup.eclassserver.exception.ErrorCode
import com.mashup.eclassserver.model.dto.LoginInfoDto
import com.mashup.eclassserver.model.entity.Member
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*


object JwtSupporter {
    private const val SECRET_KEY = "TeamEclass497!"


    fun createToken(member: Member): String {
        return Jwts.builder()
            .setHeader(mapOf("typ" to "JWT", "alg" to "HS256"))
            .setClaims(mapOf("memberId" to member.memberId, "petId" to member.petId))
            .setSubject("user")
            .setExpiration(Date.from(LocalDateTime.now().plusMonths(1).atZone(ZoneId.systemDefault()).toInstant()))
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY.toByteArray())
            .compact()
    }

    fun verifyToken(jwt: String): LoginInfoDto {
        return runCatching {
            val claims = Jwts.parser()
                .setSigningKey(SECRET_KEY.toByteArray(StandardCharsets.UTF_8))
                .parseClaimsJws(jwt)
                .body

            DEFAULT_OBJECT_MAPPER.convertValue(claims, LoginInfoDto::class.java)
        }.onFailure {
            when (it) {
                is ExpiredJwtException -> throw EclassException(ErrorCode.EXPIRED_TOKEN)
                else -> throw EclassException(ErrorCode.ETC_ERROR)
            }
        }.getOrThrow()
    }

}