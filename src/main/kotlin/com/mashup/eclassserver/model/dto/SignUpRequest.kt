package com.mashup.eclassserver.model.dto

import org.springframework.web.multipart.MultipartFile

data class SignUpRequest(
    val email: String,
    val password: String,
    val nickname: String,
)
