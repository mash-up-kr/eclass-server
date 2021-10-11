package com.mashup.eclassserver.exception

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class ErrorResponseDto(
    val errorCode: String,
    val errorMessage: String
) {
    constructor(errorCode: ErrorCode) : this(errorCode.name, errorCode.description)
}