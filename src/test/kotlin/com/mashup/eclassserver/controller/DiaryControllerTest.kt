package com.mashup.eclassserver.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.mashup.eclassserver.model.dto.AttachedStickerDto
import com.mashup.eclassserver.model.dto.DiarySubmitRequest
import com.mashup.eclassserver.model.dto.PictureSubmitRequest
import com.mashup.eclassserver.model.entity.Member
import com.mashup.eclassserver.model.repository.MemberRepository
import com.mashup.eclassserver.service.DiaryService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.doNothing
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.headers.HeaderDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import java.util.*

@WebMvcTest(DiaryController::class)
class DiaryControllerTest : AbstractTestRestDocs() {

    @MockBean
    lateinit var diaryService: DiaryService

    @MockBean
    lateinit var memberRepository: MemberRepository

    @Test
    fun diarySubmitTest() {
        val testMember = Member(1, 1, "testNick")
        val testRequest = DiarySubmitRequest(
            "test",
            arrayListOf(
                PictureSubmitRequest(
                    "testImgUrl.com",
                    false,
                    arrayListOf(
                        AttachedStickerDto(
                            1, 33.3, 44.4
                        )
                    )
                )
            )
        )

        doNothing().`when`(diaryService).submitDiary(testRequest, testMember)
        `when`(memberRepository.findById(1)).thenReturn(Optional.of(testMember))

        val mapper = ObjectMapper()
        val jsonString = mapper.writeValueAsString(testRequest)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/diary")
                    .content(jsonString)
                    .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(MockMvcResultHandlers.print())
                .andDo(
                    MockMvcRestDocumentation.document(
                        "api/v1/diary/post",
                        HeaderDocumentation.responseHeaders(),
                        PayloadDocumentation.requestFields(
                            PayloadDocumentation.fieldWithPath("content")
                                    .description("내용"),
                            PayloadDocumentation.fieldWithPath("pictureSubmitRequestList")
                                    .description("제출된 사진들"),
                            PayloadDocumentation.fieldWithPath("pictureSubmitRequestList[*].imageUrl")
                                    .description("사진 이미지"),
                            PayloadDocumentation.fieldWithPath("pictureSubmitRequestList[*].thumbnail")
                                    .description("썸네일 여부"),
                            PayloadDocumentation.fieldWithPath("pictureSubmitRequestList[*].attachedStickerDtoList")
                                    .description("스티커 정보"),
                            PayloadDocumentation.fieldWithPath("pictureSubmitRequestList[*].attachedStickerDtoList[*].stickerId")
                                    .description("스티커 아이디"),
                            PayloadDocumentation.fieldWithPath("pictureSubmitRequestList[*].attachedStickerDtoList[*].stickerX")
                                    .description("스티커 x 좌표 비율"),
                            PayloadDocumentation.fieldWithPath("pictureSubmitRequestList[*].attachedStickerDtoList[*].stickerY")
                                    .description("스티커 y 좌표 비율")
                        )
                    )
                )
    }
}