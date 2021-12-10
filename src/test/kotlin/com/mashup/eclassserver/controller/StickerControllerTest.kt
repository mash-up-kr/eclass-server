package com.mashup.eclassserver.controller

import com.mashup.eclassserver.model.dto.StickerDto
import com.mashup.eclassserver.service.StickerService
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers

@WebMvcTest(StickerController::class)
internal class StickerControllerTest : AbstractTestRestDocs() {
    @MockBean
    lateinit var stickerService: StickerService

    @Test
    fun getStickerList() {
        val stickerDtoList: List<StickerDto> = listOf(
            StickerDto(1L, "smile", "smileUrl.jpg"),
            StickerDto(2L, "car", "car.jpg")
        )

        given(stickerService.getStickers()).willReturn(stickerDtoList)

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/sticker"))
                .andDo(MockMvcResultHandlers.print())
                .andDo(
                    MockMvcRestDocumentation.document(
                        "sticker/{methodName}"
                    )
                )
    }
}