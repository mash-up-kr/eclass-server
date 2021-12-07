package com.mashup.eclassserver.config

import com.mashup.eclassserver.exception.EclassException
import com.mashup.eclassserver.exception.ErrorCode
import com.mashup.eclassserver.model.dto.LoginInfoDto
import com.mashup.eclassserver.model.dto.LoginInfoWithPetDto
import com.mashup.eclassserver.supporter.JwtSupporter
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class LoginInfo

@Component
class LoginInfoResolver : HandlerMethodArgumentResolver {

    companion object {
        const val AUTH_TOKEN = "eclass-auth-token"
    }

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(LoginInfo::class.java) && (parameter.parameter.type == LoginInfoDto::class.java || parameter.parameter.type == LoginInfoWithPetDto::class.java)
    }

    override fun resolveArgument(parameter: MethodParameter, mavContainer: ModelAndViewContainer?, webRequest: NativeWebRequest, binderFactory: WebDataBinderFactory?): Any? {
        val jwtToken = webRequest.getHeader(AUTH_TOKEN) ?: throw EclassException(ErrorCode.NOT_EXIST_AUTH_TOKEN)

        val loginInfoDto = JwtSupporter.verifyToken(jwtToken)

        return when(parameter.parameter.type) {
            LoginInfoDto::class.java -> loginInfoDto
            LoginInfoWithPetDto::class.java -> {
                loginInfoDto.petId?.let {
                    LoginInfoWithPetDto(loginInfoDto.memberId, it)
                } ?: throw EclassException(ErrorCode.HANDLE_ACCESS_DENIED, "not exist petId -> memberId: ${loginInfoDto.memberId}")
            }
            else -> throw IllegalArgumentException("illegal parameter -> type: ${parameter.parameter.type}")
        }
    }
}