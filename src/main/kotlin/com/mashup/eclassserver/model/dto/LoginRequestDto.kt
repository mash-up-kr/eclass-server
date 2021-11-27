package com.mashup.eclassserver.model.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

data class LoginRequestDto(
    @field:NotEmpty(message="이메일 주소를 입력하세요.")
    @field:NotBlank
    @field:Email(message="이메일 주소가 올바르지 않습니다.")
    val email: String,
    @field:NotEmpty
    @field:NotBlank
    val password: String
)
