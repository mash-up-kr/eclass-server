package com.mashup.eclassserver.exception

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.http.HttpStatus

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class ErrorCode(val httpStatus: Int, val description: String) {
    // Not Found Exception(Add more below...)
    DIARY_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Diary Not Found"),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Member Not Found"),
    COVER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Cover Not Found"),
    BADGE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Badge Not Found"),
    DIARY_PICTURE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Diary Picture Not Found"),
    REPLY_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Reply Not Found"),

    // Common
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST.value(), "Invalid Input Value"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED.value(), "Method Not Allowed"),
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Entity Not Found"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server Error"),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST.value(), " Invalid Type Value"),
    HANDLE_ACCESS_DENIED(HttpStatus.FORBIDDEN.value(), "Access is Denied"),
    ETC_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "내부 서버 오류입니다.")
}