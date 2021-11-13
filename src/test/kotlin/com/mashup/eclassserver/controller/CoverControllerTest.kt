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
                        parameterWithName("targetDate").description("커버 날짜 데이터(yyMM)")
                    ),
                    responseFields(
                        fieldWithPath("imageUrl").type(JsonFieldType.STRING).description("커버 image url"),
                        fieldWithPath("attachedStickerList[]").type(JsonFieldType.ARRAY).description("스티커 정보 리스트,"),
                        fieldWithPath("attachedStickerList[].imageUrl").type(JsonFieldType.STRING).description("스티커 image url"),
                        fieldWithPath("attachedStickerList[].stickerX").type(JsonFieldType.NUMBER).description("스티커 x 좌표"),
                        fieldWithPath("attachedStickerList[].stickerY").type(JsonFieldType.NUMBER).description("스티커 y 좌표")
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
                        partWithName("imageFile").description("커버 이미지"),
                        partWithName("coverData").description("커버 요청 데이터")
                    ),
                    requestPartFields(
                        "coverData",
                        fieldWithPath("color").description("색상 값"),
                        fieldWithPath("shapeType").description("cropping 타입"),
                        fieldWithPath("shapeX").description("cropping x 좌표 비율"),
                        fieldWithPath("shapeY").description("cropping Y 좌표 비율"),
                        fieldWithPath("targetDate").description("커버 타겟 날짜(년월)"),
                        fieldWithPath("attachedStickerList").description("적용된 스티커 리스트"),
                        fieldWithPath("attachedStickerList[].stickerId").description("스티커 id"),
                        fieldWithPath("attachedStickerList[].stickerX").description("스티커 x 좌표 비율"),
                        fieldWithPath("attachedStickerList[].stickerY").description("스티커 y 좌표 비율")
                    )
                )
            )
    }
}