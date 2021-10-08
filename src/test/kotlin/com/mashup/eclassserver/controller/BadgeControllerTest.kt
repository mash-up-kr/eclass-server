package com.mashup.eclassserver.controller

import com.mashup.eclassserver.model.dto.BadgeResponse
import com.mashup.eclassserver.model.dto.BadgeResponseDto
import com.mashup.eclassserver.model.entity.Badge
import com.mashup.eclassserver.service.BadgeService
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.restdocs.headers.HeaderDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(BadgeController::class)
internal class BadgeControllerTest : AbstractTestRestDocs() {

    @MockBean
    lateinit var badgeService: BadgeService

    @Test
    fun getBadgeList() {

        val badgeList: BadgeResponse = BadgeResponse(listOf(
                BadgeResponseDto.of(Badge(1L, "심술쟁이", "image-url-1")),
                BadgeResponseDto.of(Badge(2L, "산책 오지게 했다", "image-url-2"))))

        given(badgeService.getBadgeList()).willReturn(badgeList)

        mockMvc.perform(MockMvcRequestBuilders.get("/list"))
                .andDo(MockMvcResultHandlers.print())
                .andDo(
                        MockMvcRestDocumentation.document(
                                "badge/{method}",
                                HeaderDocumentation.responseHeaders(),
                                PayloadDocumentation.responseFields(
                                        PayloadDocumentation.fieldWithPath("badgeList")
                                                .description("뱃지 목록"),
                                        PayloadDocumentation.fieldWithPath("badgeList[*].name")
                                                .description("뱃지 이름"),
                                        PayloadDocumentation.fieldWithPath("badgeList[*].imageUrl")
                                                .description("뱃지 이미지 URL")

                                )
                        )
                )
    }
}