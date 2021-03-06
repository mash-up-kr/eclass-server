package com.mashup.eclassserver.controller

import com.mashup.eclassserver.TestUtils
import com.mashup.eclassserver.config.LoginInfoResolver
import com.mashup.eclassserver.constants.DEFAULT_OBJECT_MAPPER
import com.mashup.eclassserver.infra.ECLogger
import com.mashup.eclassserver.model.dto.LoginRequestDto
import com.mashup.eclassserver.model.dto.LoginResponseDto
import com.mashup.eclassserver.model.dto.SignUpRequestDto
import com.mashup.eclassserver.service.MemberService
import com.mashup.eclassserver.supporter.JwtSupporter
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(MemberController::class)
class MemberControllerTest : AbstractTestRestDocs() {

    @MockBean
    lateinit var memberService: MemberService

    companion object : ECLogger {
        const val MEMBER_BASE_URL = "/api/v1/member"
    }

    @Test
    fun signUpTest() {
        val signUpRequestDto = SignUpRequestDto("test.com", "1234", "testNick", "imageUrl.com")

        mockMvc.perform(
            post("$MEMBER_BASE_URL/signUp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(DEFAULT_OBJECT_MAPPER.writeValueAsString(signUpRequestDto))

        )
            .andDo(MockMvcResultHandlers.print())
            .andDo(
                MockMvcRestDocumentation.document(
                    "member/{methodName}",
                    PayloadDocumentation.requestFields(
                        PayloadDocumentation.fieldWithPath("email").description("?????????"),
                        PayloadDocumentation.fieldWithPath("password").description("????????????"),
                        PayloadDocumentation.fieldWithPath("nickname").description("?????????"),
                        PayloadDocumentation.fieldWithPath("imageUrl").description("????????? url")
                    )
                )
            )
    }

    @Test
    fun login() {
        val loginResponseDto = LoginRequestDto(email = "whdgus8219@naver.com", password = "samplePassword")
        whenever(memberService.login(any())).thenReturn(LoginResponseDto(JwtSupporter.createToken(TestUtils.mockMember())))

        mockMvc.perform(
            MockMvcRequestBuilders.post("$MEMBER_BASE_URL/login")
                .content(DEFAULT_OBJECT_MAPPER.writeValueAsString(loginResponseDto))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "member/{methodName}",
                    PayloadDocumentation.requestFields(
                        PayloadDocumentation.fieldWithPath("email").description("?????????"),
                        PayloadDocumentation.fieldWithPath("password").description("????????????"),
                    ),
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("token").type(JsonFieldType.STRING).description("????????? ??????")
                    )
                )
            )
    }

}