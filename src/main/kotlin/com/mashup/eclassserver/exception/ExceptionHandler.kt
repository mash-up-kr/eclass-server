package com.mashup.eclassserver.exception

import com.mashup.eclassserver.infra.ECLogger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.lang.StringBuilder
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
class ExceptionHandler {

    companion object : ECLogger
    @ExceptionHandler(EclassException::class)
    fun eclassExceptionHandler(request: HttpServletRequest, e: EclassException): ResponseEntity<ErrorResponseDto> {
        log.error("Error [eclass exception] -> param: ${getParamKeyValueString(request)}", e)
        return ResponseEntity
                .status(e.errorCode.httpStatus)
                .body(ErrorResponseDto(e.errorCode))
    }

    @ExceptionHandler(Exception::class)
    fun etcExceptionHandler(request: HttpServletRequest, e: Exception): ResponseEntity<ErrorResponseDto> {
        log.error("Error [etc exception] -> param: ${getParamKeyValueString(request)}", e)
        return ResponseEntity
                .status(ErrorCode.ETC_ERROR.httpStatus)
                .body(ErrorResponseDto(ErrorCode.ETC_ERROR))
    }

    private fun getParamKeyValueString(request: HttpServletRequest): String {
        return StringBuilder().apply {
            this.append("[")
            request.parameterNames.toList().forEach { key ->
                this.append(" $key").append("=")
                request.parameterMap[key]?.forEach { this.append(it) }
            }
            this.append("]")
        }.toString()
    }
}