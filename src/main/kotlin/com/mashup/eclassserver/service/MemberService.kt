package com.mashup.eclassserver.service

import com.mashup.eclassserver.exception.EclassException
import com.mashup.eclassserver.exception.ErrorCode
import com.mashup.eclassserver.model.dto.SignUpRequestDto
import com.mashup.eclassserver.model.entity.Member
import com.mashup.eclassserver.model.repository.MemberRepository
import com.mashup.eclassserver.supporter.S3Supporter
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val s3Supporter: S3Supporter,
    private val passwordEncoder: PasswordEncoder
) {

    @Transactional
    fun signUp(signUpRequest: SignUpRequestDto, imageFile: MultipartFile?): Member {
        validateSignUpData(signUpRequest.email)
        val member = Member(
                email = signUpRequest.email,
                password = passwordEncoder.encode(signUpRequest.password),
                nickname = signUpRequest.nickname
        )
        imageFile?.let {
            val imageUrl = s3Supporter.transmit(imageFile, S3Supporter.MEMBERS)
            member.imageUrl = imageUrl.url
        }
        return memberRepository.save(member)
    }

    fun validateSignUpData(email: String) {
        if (memberRepository.existsByEmail(email)) {
            throw EclassException(ErrorCode.DUPLICATE_EMAIL)
        }
    }

}
