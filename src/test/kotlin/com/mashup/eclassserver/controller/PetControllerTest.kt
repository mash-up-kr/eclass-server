package com.mashup.eclassserver.controller

import com.mashup.eclassserver.constants.DEFAULT_OBJECT_MAPPER
import com.mashup.eclassserver.infra.ECLogger
import com.mashup.eclassserver.model.dto.PetPostDto
import com.mashup.eclassserver.model.dto.PetResponseDto
import com.mashup.eclassserver.service.PetService
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDateTime

@WebMvcTest(PetController::class)
internal class PetControllerTest : AbstractTestRestDocs() {
    @MockBean
    private lateinit var petService: PetService

    companion object : ECLogger {
        const val COVER_BASE_URL = "/api/v1/pet"
    }

    @Test
    fun savePetTest() {
        val imageFile = MockMultipartFile("imageFile", ByteArray(30))
        val petPostDto = PetPostDto(
            name = "testName",
            birthDate = LocalDateTime.now()
        )
        val petData = MockMultipartFile("coverData", "", MediaType.APPLICATION_JSON_VALUE, DEFAULT_OBJECT_MAPPER.writeValueAsString(petPostDto).toByteArray())
        mockMvc.perform(
            MockMvcRequestBuilders.multipart(PetControllerTest.COVER_BASE_URL)
                    .file(imageFile)
                    .file(petData)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andDo(
                    MockMvcRestDocumentation.document(
                        "pet/{methodName}",
                        RequestDocumentation.requestParts(
                            RequestDocumentation.partWithName("imageFile").description("펫 이미지"),
                            RequestDocumentation.partWithName("petPostDto").description("펫 요청 데이터")
                        ),
                        PayloadDocumentation.requestPartFields(
                            "petPostDto",
                            PayloadDocumentation.fieldWithPath("color").description("색상 값"),
                            PayloadDocumentation.fieldWithPath("shapeType").description("cropping 타입"),
                            PayloadDocumentation.fieldWithPath("shapeX").description("cropping x 좌표 비율"),
                            PayloadDocumentation.fieldWithPath("shapeY").description("cropping Y 좌표 비율"),
                            PayloadDocumentation.fieldWithPath("targetDate").description("커버 타겟 날짜(년월)"),
                            PayloadDocumentation.fieldWithPath("attachedStickerList").description("적용된 스티커 리스트"),
                            PayloadDocumentation.fieldWithPath("attachedStickerList[].stickerId").description("스티커 id"),
                            PayloadDocumentation.fieldWithPath("attachedStickerList[].stickerX").description("스티커 x 좌표 비율"),
                            PayloadDocumentation.fieldWithPath("attachedStickerList[].stickerY").description("스티커 y 좌표 비율")
                        )
                    )
                )
    }
}