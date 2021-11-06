package com.mashup.eclassserver.controller

import com.mashup.eclassserver.constants.DEFAULT_OBJECT_MAPPER
import com.mashup.eclassserver.infra.ECLogger
import com.mashup.eclassserver.model.dto.PetEditDto
import com.mashup.eclassserver.model.dto.PetPostDto
import com.mashup.eclassserver.model.entity.Member
import com.mashup.eclassserver.model.entity.Pet
import com.mashup.eclassserver.model.repository.MemberRepository
import com.mashup.eclassserver.service.PetService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.restdocs.headers.HeaderDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.RequestPostProcessor
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDateTime
import java.util.*

@WebMvcTest(PetController::class)
internal class PetControllerTest : AbstractTestRestDocs() {
    @MockBean
    private lateinit var petService: PetService

    @MockBean
    private lateinit var memberRepository: MemberRepository

    companion object : ECLogger {
        const val PET_BASE_URL = "/api/v1/pet"
    }

    @Test
    fun savePetTest() {
        val imageFile = MockMultipartFile("imageFile", ByteArray(30))
        val petPostDto = PetPostDto(
            name = "testName",
            birthDate = LocalDateTime.now()
        )
        val petData = MockMultipartFile("petPostDto", "", MediaType.APPLICATION_JSON_VALUE, DEFAULT_OBJECT_MAPPER.writeValueAsString(petPostDto).toByteArray())
        mockMvc.perform(
            MockMvcRequestBuilders.multipart(PET_BASE_URL)
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
                            PayloadDocumentation.fieldWithPath("name").description("펫 이름"),
                            PayloadDocumentation.fieldWithPath("birthDate").description("생일 ex)2020-01-01"),
                        )
                    )
                )
    }

    @Test
    fun getPetTest() {
        val testMember = Member(1, 1, "testNick")
        val testPet = Pet(1, "testPet", LocalDateTime.now(), "http://testPetUrl.com")
        `when`(memberRepository.findById(1)).thenReturn(Optional.of(testMember))
        `when`(petService.findPet(1)).thenReturn(testPet)
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/pet"))
                .andDo(MockMvcResultHandlers.print())
                .andDo(
                    MockMvcRestDocumentation.document(
                        "api/v1/pet/get",
                        HeaderDocumentation.responseHeaders(),
                        PayloadDocumentation.responseFields(
                            PayloadDocumentation.fieldWithPath("petId")
                                    .description("펫 아이디"),
                            PayloadDocumentation.fieldWithPath("name")
                                    .description("펫 이름"),
                            PayloadDocumentation.fieldWithPath("birthDate")
                                    .description("펫 생년월일"),
                            PayloadDocumentation.fieldWithPath("imageUrl")
                                    .description("펫 이미지 url"),
                        )
                    )
                )
    }

    @Test
    fun editPetTest() {
        val imageFile = MockMultipartFile("imageFile", ByteArray(30))
        val petEditDto = PetEditDto(
            name = "editName",
            birthDate = LocalDateTime.now()
        )
        val postProcess = RequestPostProcessor { it.method = "PUT"; it }
        val petData = MockMultipartFile("petEditDto", "", MediaType.APPLICATION_JSON_VALUE, DEFAULT_OBJECT_MAPPER.writeValueAsString(petEditDto).toByteArray())
        mockMvc.perform(
            MockMvcRequestBuilders.multipart(PET_BASE_URL + "/1")
                    .file(imageFile)
                    .file(petData)
                    .with(postProcess)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andDo(
                    MockMvcRestDocumentation.document(
                        "pet/{methodName}",
                        RequestDocumentation.requestParts(
                            RequestDocumentation.partWithName("imageFile").description("펫 이미지"),
                            RequestDocumentation.partWithName("petEditDto").description("펫 요청 데이터")
                        ),
                        PayloadDocumentation.requestPartFields(
                            "petEditDto",
                            PayloadDocumentation.fieldWithPath("name").description("펫 이름"),
                            PayloadDocumentation.fieldWithPath("birthDate").description("생일 ex)2020-01-01"),
                        )
                    )
                )
    }
}