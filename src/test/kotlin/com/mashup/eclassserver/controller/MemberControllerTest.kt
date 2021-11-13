package com.mashup.eclassserver.controller

import com.mashup.eclassserver.constants.DEFAULT_OBJECT_MAPPER
import com.mashup.eclassserver.infra.ECLogger
import com.mashup.eclassserver.model.dto.SignUpRequestDto
import com.mashup.eclassserver.service.MemberService
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers

@WebMvcTest(MemberController::class)
class MemberControllerTest : AbstractTestRestDocs() {

    @MockBean
    lateinit var memberService: MemberService

    companion object : ECLogger {
        const val MEMBER_BASE_URL = "/api/v1/member"
    }

    @Test
    fun signUpTest() {
        val imageFile = MockMultipartFile("imageFile", ByteArray(30))
        val signUpRequestDto = SignUpRequestDto("test.com", "1234", "testNick")
        val signUpRequest = MockMultipartFile("signUpRequestDto", "", MediaType.APPLICATION_JSON_VALUE, DEFAULT_OBJECT_MAPPER.writeValueAsString(signUpRequestDto).toByteArray())

        mockMvc.perform(
                MockMvcRequestBuilders.multipart("$MEMBER_BASE_URL/signUp")
                        .file(imageFile)
                        .file(signUpRequest)
        )
                .andDo(MockMvcResultHandlers.print())
                .andDo(
                        MockMvcRestDocumentation.document(
                                "member/{methodName}",
                                RequestDocumentation.requestParts(
                                        RequestDocumentation.partWithName("imageFile").description("멤버 프로필 이미지"),
                                        RequestDocumentation.partWithName("signUpRequestDto").description("회원가입 요청 데이터")
                                ),
                                PayloadDocumentation.requestPartFields(
                                        "signUpRequestDto",
                                        PayloadDocumentation.fieldWithPath("email").description("이메일"),
                                        PayloadDocumentation.fieldWithPath("password").description("비밀번호"),
                                        PayloadDocumentation.fieldWithPath("nickname").description("닉네임")
                                )
                        )
                )
    }

}