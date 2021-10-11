package com.mashup.eclassserver.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.mashup.eclassserver.constants.DEFAULT_OBJECT_MAPPER
import com.mashup.eclassserver.model.dto.*
import com.mashup.eclassserver.model.entity.Member
import com.mashup.eclassserver.model.repository.MemberRepository
import com.mashup.eclassserver.service.DiaryService
import com.mashup.eclassserver.service.ReplyService
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.doNothing
import org.springframework.beans.factory.annotation.Autowired
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
class DiaryControllerTest @Autowired constructor(
    @MockBean
    private val diaryService: DiaryService,
    @MockBean
    private val memberRepository: MemberRepository,
    @MockBean
    private val replyService: ReplyService
) : AbstractTestRestDocs() {
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
                MockMvcRequestBuilders.post("/diary")
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
                                                .description("스티커 y 좌표 비율"),
                                )
                        )
                )
    }

    @Test
    fun replyEditTest() {
        registerReply()
        val replyRequest = ReplyEditRequest("edit content")
        BDDMockito.given(replyService.editReply(1L, 1L, replyRequest)).willReturn(null)

        mockMvc.perform(MockMvcRequestBuilders.put("/1/reply/edit/1")
                .content(DEFAULT_OBJECT_MAPPER.writeValueAsString(replyRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andDo(
                        MockMvcRestDocumentation.document(
                                "diary/{method}",
                                HeaderDocumentation.responseHeaders(),
                                PayloadDocumentation.requestFields(
                                        PayloadDocumentation.fieldWithPath("content")
                                                .description("내용"),
                                ),
                                HeaderDocumentation.responseHeaders()
                        )
                )
    }

    @Test
    fun replyDeleteTest() {
        registerReply()
        BDDMockito.given(replyService.deleteReply(1L, 1L)).willReturn(null)

        mockMvc.perform(MockMvcRequestBuilders.delete("/1/reply/delete/1"))
                .andDo(MockMvcResultHandlers.print())
                .andDo(
                        MockMvcRestDocumentation.document(
                                "diary/{method}",
                                HeaderDocumentation.responseHeaders(),
                                HeaderDocumentation.responseHeaders()
                        )
                )
    }

    @Test
    fun getReplyListTest() {
        registerReply()
        val member = Member(1, 1, "test")
        val replyList = ReplyResponse(listOf(ReplyResponseDto(member, "content")))
        BDDMockito.given(replyService.getReplyList(1L)).willReturn(replyList)

        mockMvc.perform(MockMvcRequestBuilders.get("/1/reply/list"))
                .andDo(MockMvcResultHandlers.print())
                .andDo(
                        MockMvcRestDocumentation.document(
                                "diary/{method}",
                                HeaderDocumentation.requestHeaders(),
                                HeaderDocumentation.responseHeaders(),
                                PayloadDocumentation.responseFields(
                                        PayloadDocumentation.fieldWithPath("replyList")
                                                .description("댓글 목록"),
                                        PayloadDocumentation.fieldWithPath("replyList[*].member")
                                                .description("댓글 작성자 정보"),
                                        PayloadDocumentation.fieldWithPath("badgeList[*].content")
                                                .description("댓글 내용")
                                )
                        )
                )
    }

    private fun registerReply() {
        val member = Member(1, 1, "test")
        val replyRequest = ReplyRegisterRequest("content")
        BDDMockito.given(replyService.registerReply(1L, replyRequest, member)).willReturn(null)
    }
}