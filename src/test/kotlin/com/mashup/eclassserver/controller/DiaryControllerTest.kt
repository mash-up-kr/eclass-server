package com.mashup.eclassserver.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.mashup.eclassserver.constants.DEFAULT_OBJECT_MAPPER
import com.mashup.eclassserver.infra.ECLogger
import com.mashup.eclassserver.model.dto.*
import com.mashup.eclassserver.model.entity.Badge
import com.mashup.eclassserver.model.entity.Diary
import com.mashup.eclassserver.model.entity.Member
import com.mashup.eclassserver.model.repository.MemberRepository
import com.mashup.eclassserver.service.BadgeService
import com.mashup.eclassserver.service.DiaryService
import com.mashup.eclassserver.service.ReplyService
import com.nhaarman.mockitokotlin2.given
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.headers.HeaderDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import java.time.LocalDateTime
import java.util.*

@WebMvcTest(DiaryController::class)
class DiaryControllerTest : AbstractTestRestDocs() {

    @MockBean
    lateinit var diaryService: DiaryService

    @MockBean
    lateinit var memberRepository: MemberRepository

    @MockBean
    lateinit var replyService: ReplyService

    @MockBean
    lateinit var badgeService: BadgeService

    companion object : ECLogger {
        const val DIARY_BASE_URL = "/api/v1/diary"
    }

    @Test
    fun diarySubmitTest() {
        val testMember = Member(1, 1, "testNick", "test.com", "1234")
        val testRequest = DiaryRequestDto(
            "test",
            arrayListOf(
                PictureRequestDto(
                    "testImgUrl.com",
                    false,
                    arrayListOf(
                        AttachedStickerDto(
                            1, 33.3, 44.4
                        )
                    )
                )
            ),
            1
        )
        val testBadge = Badge(1, "testBadge", "http://testBadge.com")
        `when`(diaryService.submitDiary(testRequest, testMember)).thenReturn(Diary.of(testRequest, testMember))
        doNothing().`when`(diaryService).saveBadge(0, testBadge)
        `when`(badgeService.findBadgeById(anyLong())).thenReturn(testBadge)
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
                            PayloadDocumentation.fieldWithPath("badgeId")
                                    .description("뱃지 id"),
                            PayloadDocumentation.fieldWithPath("pictureList")
                                    .description("제출된 사진들"),
                            PayloadDocumentation.fieldWithPath("pictureList[*].imageUrl")
                                    .description("사진 이미지"),
                            PayloadDocumentation.fieldWithPath("pictureList[*].thumbnail")
                                    .description("썸네일 여부(thumbnail아님!!! isThumbnail로 줘야함"),
                            PayloadDocumentation.fieldWithPath("pictureList[*].attachedStickerDtoList")
                                    .description("스티커 정보"),
                            PayloadDocumentation.fieldWithPath("pictureList[*].attachedStickerDtoList[*].stickerId")
                                    .description("스티커 아이디"),
                            PayloadDocumentation.fieldWithPath("pictureList[*].attachedStickerDtoList[*].stickerX")
                                    .description("스티커 x 좌표 비율"),
                            PayloadDocumentation.fieldWithPath("pictureList[*].attachedStickerDtoList[*].stickerY")
                                    .description("스티커 y 좌표 비율"),
                        )
                    )
                )
    }

    @Test
    fun replyRegisterTest() {
        val member = Member(1, 1, "test", "test.com", "1234")
        val replyRequest = ReplyRegisterRequest("content")

        mockMvc.perform(
            MockMvcRequestBuilders.post("$DIARY_BASE_URL/{diaryId}/reply/register", 1L)
                    .content(DEFAULT_OBJECT_MAPPER.writeValueAsString(replyRequest))
                    .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(MockMvcResultHandlers.print())
                .andDo(
                    MockMvcRestDocumentation.document(
                        "diary/{methodName}",
                        HeaderDocumentation.responseHeaders(),
                        PayloadDocumentation.requestFields(
                            PayloadDocumentation.fieldWithPath("content")
                                    .description("댓글 내용"),
                        ),
                        HeaderDocumentation.responseHeaders()
                    )
                )
    }

    @Test
    fun replyEditTest() {
        val replyRequest = ReplyEditRequest("edit content")
        doNothing().`when`(replyService).editReply(1L, 1L, replyRequest)

        mockMvc.perform(
            MockMvcRequestBuilders.put("$DIARY_BASE_URL/{diaryId}/reply/edit/{replyId}", 1L, 1L)
                    .content(DEFAULT_OBJECT_MAPPER.writeValueAsString(replyRequest))
                    .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(MockMvcResultHandlers.print())
                .andDo(
                    MockMvcRestDocumentation.document(
                        "diary/{methodName}",
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
        doNothing().`when`(replyService).deleteReply(1L, 1L)

        mockMvc.perform(MockMvcRequestBuilders.delete("$DIARY_BASE_URL/{diaryId}/reply/delete/{replyId}", 1L, 1L))
                .andDo(MockMvcResultHandlers.print())
                .andDo(
                    MockMvcRestDocumentation.document(
                        "diary/{methodName}",
                        HeaderDocumentation.responseHeaders(),
                        HeaderDocumentation.responseHeaders()
                    )
                )
    }

    @Test
    fun getReplyListTest() {
        val member = Member(1, 1, "test", "test.com", "1234")
        val replyList = ReplyResponse(listOf(ReplyResponseDto("testMember", "content")))
        given(replyService.getReplyList(1L)).willReturn(replyList)

        mockMvc.perform(MockMvcRequestBuilders.get("$DIARY_BASE_URL/{diaryId}/reply", 1L))
                .andDo(MockMvcResultHandlers.print())
                .andDo(
                    MockMvcRestDocumentation.document(
                        "diary/{methodName}",
                        HeaderDocumentation.requestHeaders(),
                        HeaderDocumentation.responseHeaders(),
                        PayloadDocumentation.responseFields(
                            PayloadDocumentation.fieldWithPath("replyList")
                                    .description("댓글 목록"),
                            PayloadDocumentation.fieldWithPath("replyList[*].memberName")
                                    .description("댓글 작성자 이름"),
                            PayloadDocumentation.fieldWithPath("replyList[*].content")
                                    .description("댓글 내용")
                        )
                    )
                )
    }

    @Test
    fun getDiaryIdListTest() {
        val member = Member(1, 1, "test", "test.com", "1234")
        `when`(memberRepository.findById(1)).thenReturn(Optional.of(member))
        val diaryIdList = listOf(1L, 2L, 3L)
        given(diaryService.getDiaryIdList(member)).willReturn(diaryIdList)

        mockMvc.perform(MockMvcRequestBuilders.get("$DIARY_BASE_URL"))
                .andDo(MockMvcResultHandlers.print())
                .andDo(
                    MockMvcRestDocumentation.document(
                        "api/v1/diary/get",
                        HeaderDocumentation.requestHeaders(),
                        HeaderDocumentation.responseHeaders(),
                        PayloadDocumentation.responseFields(
                            PayloadDocumentation.fieldWithPath("[]")
                                    .description("내용"),
                        )
                    )
                )
    }

    @Test
    fun getDiaryTest() {
        val diaryDto = DiaryResponseDto(
            1,
            "test",
            arrayListOf(
                PictureSubmitRequest(
                    1,
                    "testImgUrl.com",
                    false,
                    arrayListOf(
                        AttachedStickerDto(
                            1, 33.3, 44.4
                        )
                    )
                )
            ),
            BadgeResponseDto(
                1,
                "testName",
                "http:testbadge.com"
            ),
            createdAt = LocalDateTime.now()
        )
        given(diaryService.findDiaryById(1)).willReturn(diaryDto)

        mockMvc.perform(MockMvcRequestBuilders.get("$DIARY_BASE_URL/1"))
                .andDo(MockMvcResultHandlers.print())
                .andDo(
                    MockMvcRestDocumentation.document(
                        "api/v1/diary/byId",
                        HeaderDocumentation.requestHeaders(),
                        HeaderDocumentation.responseHeaders(),
                        PayloadDocumentation.responseFields(
                            PayloadDocumentation.fieldWithPath("diaryId")
                                    .description("다이어리 id"),
                            PayloadDocumentation.fieldWithPath("content")
                                    .description("내용"),
                            PayloadDocumentation.fieldWithPath("createdAt")
                                    .description("작성일"),
                            PayloadDocumentation.fieldWithPath("badgeResponseDto")
                                    .description("뱃지 정보"),
                            PayloadDocumentation.fieldWithPath("badgeResponseDto.badgeId")
                                    .description("뱃지 id"),
                            PayloadDocumentation.fieldWithPath("badgeResponseDto.name")
                                    .description("뱃지 이름"),
                            PayloadDocumentation.fieldWithPath("badgeResponseDto.imageUrl")
                                    .description("뱃지 이미지 url"),
                            PayloadDocumentation.fieldWithPath("pictureSubmitRequestList")
                                    .description("제출된 사진들"),
                            PayloadDocumentation.fieldWithPath("pictureSubmitRequestList[*].diaryPictureId")
                                    .description("다이어리 id"),
                            PayloadDocumentation.fieldWithPath("pictureSubmitRequestList[*].imageUrl")
                                    .description("사진 이미지"),
                            PayloadDocumentation.fieldWithPath("pictureSubmitRequestList[*].isThumbnail")
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

    @Test
    fun getDiaryListTest() {
        val diaryDto = DiaryResponseDto(
            1,
            "test",
            arrayListOf(
                PictureSubmitRequest(
                    1,
                    "testImgUrl.com",
                    false,
                    arrayListOf(
                        AttachedStickerDto(
                            1, 33.3, 44.4
                        )
                    )
                )
            ),
            BadgeResponseDto(
                1,
                "testName",
                "http:testbadge.com"
            ),
            createdAt = LocalDateTime.now()
        )

        val diaryDto2 = DiaryResponseDto(
            4,
            "test4",
            arrayListOf(
                PictureSubmitRequest(
                    3,
                    "testImgUrl3.com",
                    true,
                    arrayListOf(
                        AttachedStickerDto(
                            2, 13.3, 44.4
                        )
                    )
                )
            ),
            BadgeResponseDto(
                3,
                "testName",
                "http:testbadge.com"
            ),
            createdAt = LocalDateTime.now()
        )
        val testMember = Member(1, 1, "testNick", "test@naver.com", "passwd")

        `when`(memberRepository.findById(1)).thenReturn(Optional.of(testMember))
        given(diaryService.getDiaryListByDate(testMember, 2021, 4)).willReturn(listOf(diaryDto, diaryDto2))

        mockMvc.perform(
            MockMvcRequestBuilders.get("$DIARY_BASE_URL/list")
                    .param("year", "2021")
                    .param("month", "4")
        )
                .andDo(MockMvcResultHandlers.print())
                .andDo(
                    MockMvcRestDocumentation.document(
                        "api/v1/diary/list",
                        HeaderDocumentation.requestHeaders(),
                        HeaderDocumentation.responseHeaders(),
                        PayloadDocumentation.responseFields(
                            PayloadDocumentation.fieldWithPath("[*].diaryId")
                                    .description("다이어리 id"),
                            PayloadDocumentation.fieldWithPath("[*].content")
                                    .description("내용"),
                            PayloadDocumentation.fieldWithPath("[*].createdAt")
                                    .description("작성일"),
                            PayloadDocumentation.fieldWithPath("[*].badgeResponseDto")
                                    .description("뱃지 정보"),
                            PayloadDocumentation.fieldWithPath("[*].badgeResponseDto.badgeId")
                                    .description("뱃지 id"),
                            PayloadDocumentation.fieldWithPath("[*].badgeResponseDto.name")
                                    .description("뱃지 이름"),
                            PayloadDocumentation.fieldWithPath("[*].badgeResponseDto.imageUrl")
                                    .description("뱃지 이미지 url"),
                            PayloadDocumentation.fieldWithPath("[*].pictureSubmitRequestList")
                                    .description("제출된 사진들"),
                            PayloadDocumentation.fieldWithPath("[*].pictureSubmitRequestList[*].diaryPictureId")
                                    .description("다이어리 id"),
                            PayloadDocumentation.fieldWithPath("[*].pictureSubmitRequestList[*].imageUrl")
                                    .description("사진 이미지"),
                            PayloadDocumentation.fieldWithPath("[*].pictureSubmitRequestList[*].isThumbnail")
                                    .description("썸네일 여부"),
                            PayloadDocumentation.fieldWithPath("[*].pictureSubmitRequestList[*].attachedStickerDtoList")
                                    .description("스티커 정보"),
                            PayloadDocumentation.fieldWithPath("[*].pictureSubmitRequestList[*].attachedStickerDtoList[*].stickerId")
                                    .description("스티커 아이디"),
                            PayloadDocumentation.fieldWithPath("[*].pictureSubmitRequestList[*].attachedStickerDtoList[*].stickerX")
                                    .description("스티커 x 좌표 비율"),
                            PayloadDocumentation.fieldWithPath("[*].pictureSubmitRequestList[*].attachedStickerDtoList[*].stickerY")
                                    .description("스티커 y 좌표 비율")
                        )
                    )
                )
    }
}