package com.mashup.eclassserver.model.dto

data class SignUpRequestDto(
    val email: String,
    val password: String,
    val nickname: String,
)
