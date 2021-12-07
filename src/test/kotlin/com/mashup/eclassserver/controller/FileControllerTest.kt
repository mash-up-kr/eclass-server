package com.mashup.eclassserver.controller

import com.mashup.eclassserver.infra.ECLogger
import com.mashup.eclassserver.service.FileService
import com.nhaarman.mockitokotlin2.given
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.mock.web.MockMultipartFile
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@WebMvcTest(FileController::class)
class FileControllerTest : AbstractTestRestDocs() {
    @MockBean
    private lateinit var fileService: FileService

    companion object : ECLogger {
        const val COVER_BASE_URL = "/api/v1/file"
    }

    @Test
    fun registerImageTest() {
        var imageFile: MockMultipartFile? = MockMultipartFile("imageFile", ByteArray(30))
        given(fileService.saveImage(imageFile)).willReturn("https://eclass-bucket.s3.ap-northeast-2.amazonaws.com/diary-pictures/diaryImageFileName")

        mockMvc.perform(
            MockMvcRequestBuilders.multipart(FileControllerTest.COVER_BASE_URL + "/image")
                    .file(imageFile)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andDo(
                    MockMvcRestDocumentation.document(
                        "file/image/{methodName}",
                        RequestDocumentation.requestParts(
                            RequestDocumentation.partWithName("imageFile").description("다이어리 이미지"),
                        )
                    )
                )
    }
}