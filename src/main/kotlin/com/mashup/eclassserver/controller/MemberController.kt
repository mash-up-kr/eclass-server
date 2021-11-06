package com.mashup.eclassserver.controller

import com.mashup.eclassserver.exception.EclassException
import com.mashup.eclassserver.model.dto.SignUpRequest
import com.mashup.eclassserver.service.MemberService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/member")
class MemberController(
    private val memberService: MemberService
) {
    @PostMapping("/signUp")
    fun signUp(signUpRequest: SignUpRequest, imageFile: MultipartFile?) : ResponseEntity<*> {
        return try {
            ResponseEntity
                    .status(HttpStatus.OK)
                    .body(memberService.signUp(signUpRequest, imageFile))
        } catch (ex: EclassException) {
            ResponseEntity
                    .status(ex.errorCode.httpStatus)
                    .body(null)
        }
    }

}