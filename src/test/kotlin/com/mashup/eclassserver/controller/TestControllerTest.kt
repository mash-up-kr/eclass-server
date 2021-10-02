package com.mashup.eclassserver.controller

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers

@WebMvcTest(TestController::class)
internal class TestControllerTest : AbstractTestRestDocs() {

    @Test
    fun ping() {
        mockMvc.perform(MockMvcRequestBuilders.get("/ping"))
            .andDo(MockMvcResultHandlers.print())
            .andDo(
                document(
                    "test/{method}",
                    responseHeaders()
                )
            )
    }

}