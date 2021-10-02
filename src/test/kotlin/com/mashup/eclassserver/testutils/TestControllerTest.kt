package com.mashup.eclassserver.testutils

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document


class TestControllerTest(): AbstractTestController() {
    @DisplayName("테스트 컨트롤러 테스트")
    @Test
    @Throws(Exception::class)
    fun 테스트_컨트롤러_테스트() {
        mockMvc.perform(get("/ping"))
                .andExpect(status().isOk())
                .andDo(
                    document("ping",
                             getDocumentRequest(),
                             getDocumentResponse()
                    )
                )
    }
}