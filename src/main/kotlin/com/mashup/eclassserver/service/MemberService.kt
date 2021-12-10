package com.mashup.eclassserver.service

import com.mashup.eclassserver.exception.EclassException
import com.mashup.eclassserver.exception.ErrorCode
import com.mashup.eclassserver.model.dto.LoginRequestDto
import com.mashup.eclassserver.model.dto.LoginResponseDto
import com.mashup.eclassserver.model.dto.SignUpRequestDto
import com.mashup.eclassserver.model.dto.SignUpResponseDto
import com.mashup.eclassserver.model.entity.Member
import com.mashup.eclassserver.model.repository.MemberRepository
import com.mashup.eclassserver.supporter.JwtSupporter
import com.mashup.eclassserver.supporter.S3Supporter
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @Transactional
    fun signUp(signUpRequest: SignUpRequestDto): SignUpResponseDto {
        validateSignUpData(signUpRequest.email)
        val member = Member(
            email = signUpRequest.email,
            password = passwordEncoder.encode(signUpRequest.password),
            nickname = signUpRequest.nickname,
            imageUrl = signUpRequest.imageUrl
        )

        return SignUpResponseDto.of(memberRepository.save(member))
    }

    fun login(loginRequestDto: LoginRequestDto): LoginResponseDto {
        val member = memberRepository.findByEmailAndPassword(loginRequestDto.email, passwordEncoder.encode(loginRequestDto.password)) ?: throw EclassException(ErrorCode.INVALID_LOGIN_INFO)
        val token = JwtSupporter.createToken(member)

        return LoginResponseDto(token)
    }

    private fun validateSignUpData(email: String) {
        if (memberRepository.existsByEmail(email)) {
            throw EclassException(ErrorCode.DUPLICATE_EMAIL)
        }
    }

}
