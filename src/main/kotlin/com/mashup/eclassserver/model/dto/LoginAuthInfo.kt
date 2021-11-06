package com.mashup.eclassserver.model.dto

data class LoginAuthInfo(
    val memberId: Long,
    val petId: Long? = null
)
