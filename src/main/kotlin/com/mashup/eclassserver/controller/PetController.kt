package com.mashup.eclassserver.controller

import com.mashup.eclassserver.config.LoginInfo
import com.mashup.eclassserver.model.dto.*
import com.mashup.eclassserver.model.entity.Pet
import com.mashup.eclassserver.service.MemberService
import com.mashup.eclassserver.service.PetService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/pet")
class PetController(
    val petService: PetService,
    val memberService: MemberService
) {
    @PostMapping
    fun savePet(
        @RequestPart petPostDto: PetPostDto,
        @RequestParam imageFile: MultipartFile?,
        @LoginInfo loginInfo: LoginInfoDto
    ): ResponseEntity<Unit> {
        val member = memberService.findById(loginInfo.memberId)
        val pet = Pet.of(petPostDto)
        petService.savePet(pet, imageFile)
        memberService.registerPet(member, pet)
        return ResponseEntity
                .status(HttpStatus.OK).build()
    }

    @GetMapping
    fun getPet(@LoginInfo loginInfo: LoginInfoWithPetDto): ResponseEntity<PetResponseDto> {
        val pet = petService.findPet(loginInfo.petId)
        return ResponseEntity
                .status(HttpStatus.OK).body(PetResponseDto.of(pet))
    }

    @PutMapping()
    fun editPet(
        @RequestPart petEditDto: PetEditDto,
        @RequestParam imageFile: MultipartFile?,
        @LoginInfo loginInfo: LoginInfoWithPetDto
    ): ResponseEntity<Unit> {
        val member = memberService.findById(loginInfo.memberId)

        val pet = petService.findPet(member.petId)
        petService.editPet(pet, petEditDto, imageFile)
        return ResponseEntity
                .status(HttpStatus.OK).build()
    }
}