package com.mashup.eclassserver.controller

import com.mashup.eclassserver.constants.DEFAULT_OBJECT_MAPPER
import com.mashup.eclassserver.infra.ECLogger
import com.mashup.eclassserver.model.dto.AttachedStickerDto
import com.mashup.eclassserver.model.dto.CoverAttachedSticker
import com.mashup.eclassserver.model.dto.CoverData
import com.mashup.eclassserver.model.dto.CoverResponseDto
import com.mashup.eclassserver.model.entity.ShapeType
import com.mashup.eclassserver.service.CoverService
import com.nhaarman.mockitokotlin2.given
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.RequestDocumentation.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import kotlin.math.roundToInt
import kotlin.random.Random

@WebMvcTest(CoverController::class)
internal class CoverControllerTest : AbstractTestRestDocs() {

    @MockBean
    private lateinit var coverService: CoverService

    companion object : ECLogger {
        const val COVER_BASE_URL = "/api/v1/cover"
    }

    @Test
    fun home() {
        // given
        given(coverService.homeByMonth(1, "2111")).willReturn(
            CoverResponseDto(
                "test-cover-image-url",
                mutableListOf<CoverAttachedSticker>().apply {
                    repeat(3) {
                        this.add(
                            CoverAttachedSticker(
                                "test-sticker-image-url-$it",
                                stickerX = Random.nextDouble(5.0 * 100).roundToInt() / 100.0,
                                stickerY = Random.nextDouble(5.0 * 100).roundToInt() / 100.0
                            )
                        )
                    }
                }
            )
        )

        // when & then
        mockMvc.perform(
            RestDocumentationRequestBuilders.get("$COVER_BASE_URL/{targetDate}", "2111")
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "cover/{methodName}",
                    pathParameters(
                        parameterWithName("targetDate").description("?????? ?????? ?????????(yyMM)")
                    ),
                    responseFields(
                        fieldWithPath("imageUrl").type(JsonFieldType.STRING).description("?????? image url"),
                        fieldWithPath("attachedStickerList[]").type(JsonFieldType.ARRAY).description("????????? ?????? ?????????,"),
                        fieldWithPath("attachedStickerList[].imageUrl").type(JsonFieldType.STRING).description("????????? image url"),
                        fieldWithPath("attachedStickerList[].stickerX").type(JsonFieldType.NUMBER).description("????????? x ??????"),
                        fieldWithPath("attachedStickerList[].stickerY").type(JsonFieldType.NUMBER).description("????????? y ??????")
                    )
                )
            )
    }

    @Test
    fun register() {
        val imageFile = MockMultipartFile("imageFile", ByteArray(30))
        val coverDataDto = CoverData(
            attachedStickerList = listOf(AttachedStickerDto(1L, 3.5, 3.5)),
            color = "0xFF",
            shapeType = ShapeType.CIRCLE,
            shapeX = 5.5,
            shapeY = 5.5,
            targetDate = "2111"
        )
        log.debug("coverData -> ${DEFAULT_OBJECT_MAPPER.writeValueAsString(coverDataDto)}")
        val coverData = MockMultipartFile("coverData", "", MediaType.APPLICATION_JSON_VALUE, DEFAULT_OBJECT_MAPPER.writeValueAsString(coverDataDto).toByteArray())
        mockMvc.perform(
            MockMvcRequestBuilders.multipart(COVER_BASE_URL)
                .file(imageFile)
                .file(coverData)
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "cover/{methodName}",
                    requestParts(
                        partWithName("imageFile").description("?????? ?????????"),
                        partWithName("coverData").description("?????? ?????? ?????????")
                    ),
                    requestPartFields(
                        "coverData",
                        fieldWithPath("color").description("?????? ???"),
                        fieldWithPath("shapeType").description("cropping ??????"),
                        fieldWithPath("shapeX").description("cropping x ?????? ??????"),
                        fieldWithPath("shapeY").description("cropping Y ?????? ??????"),
                        fieldWithPath("targetDate").description("?????? ?????? ??????(??????)"),
                        fieldWithPath("attachedStickerList").description("????????? ????????? ?????????"),
                        fieldWithPath("attachedStickerList[].stickerId").description("????????? id"),
                        fieldWithPath("attachedStickerList[].stickerX").description("????????? x ?????? ??????"),
                        fieldWithPath("attachedStickerList[].stickerY").description("????????? y ?????? ??????")
                    )
                )
            )
    }
}