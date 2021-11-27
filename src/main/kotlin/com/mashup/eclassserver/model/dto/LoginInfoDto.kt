package com.mashup.eclassserver.model.dto

data class LoginInfoDto(
    val memberId: Long,
    val petId: Long? = null
)

data class LoginInfoWithPetDto(
    val memberId: Long,
    val petId: Long
)