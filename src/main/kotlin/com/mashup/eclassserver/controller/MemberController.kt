package com.mashup.eclassserver.controller

import com.mashup.eclassserver.exception.EclassException
import com.mashup.eclassserver.model.dto.SignUpRequestDto
import com.mashup.eclassserver.model.dto.SignUpResponseDto
import com.mashup.eclassserver.service.MemberService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/member")
class MemberController(
    private val memberService: MemberService
) {
    @PostMapping("/signUp")
    fun signUp(@RequestPart signUpRequestDto: SignUpRequestDto, @RequestParam imageFile: MultipartFile?) : ResponseEntity<SignUpResponseDto> {
        return try {
            ResponseEntity
                    .status(HttpStatus.OK)
                    .body(SignUpResponseDto.of(memberService.signUp(signUpRequestDto, imageFile)))
        } catch (ex: EclassException) {
            ResponseEntity
                    .status(ex.errorCode.httpStatus)
                    .body(null)
        }
    }

}