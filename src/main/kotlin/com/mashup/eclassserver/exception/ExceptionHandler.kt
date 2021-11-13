package com.mashup.eclassserver.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(EclassException::class)
    fun eclassExceptionHandler(request: HttpServletRequest, e: EclassException): ResponseEntity<ErrorResponseDto> {
        return ResponseEntity
                .status(e.errorCode.httpStatus)
                .body(ErrorResponseDto(e.errorCode))
    }

    @ExceptionHandler(Exception::class)
    fun etcExceptionHandler(request: HttpServletRequest, e: Exception): ResponseEntity<ErrorResponseDto> {
        return ResponseEntity
                .status(ErrorCode.ETC_ERROR.httpStatus)
                .body(ErrorResponseDto(ErrorCode.ETC_ERROR))
    }
}