package com.mashup.eclassserver.controller

import com.mashup.eclassserver.model.dto.PetEditDto
import com.mashup.eclassserver.model.dto.PetPostDto
import com.mashup.eclassserver.model.dto.PetResponseDto
import com.mashup.eclassserver.model.entity.Pet
import com.mashup.eclassserver.model.repository.MemberRepository
import com.mashup.eclassserver.service.PetService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/pet")
class PetController(
    val petService: PetService,
    val memberRepository: MemberRepository
) {
    @PostMapping
    fun savePet(@RequestPart petPostDto: PetPostDto, @RequestParam imageFile: MultipartFile?): ResponseEntity<Unit> {
        //JWT 멤버 꺼내오기
        val pet = Pet.of(petPostDto)
        petService.savePet(pet, imageFile)
        //member.setPetid(petid)
        return ResponseEntity
                .status(HttpStatus.OK).build()
    }

    @GetMapping
    fun getPet(): ResponseEntity<PetResponseDto> {
        val member = memberRepository.findById(1).get()

        val pet = petService.findPet(member.petId)
        return ResponseEntity
                .status(HttpStatus.OK).body(PetResponseDto.of(pet))
    }

    @PutMapping()
    fun editPet(
        @RequestPart petEditDto: PetEditDto,
        @RequestParam imageFile: MultipartFile?
    ): ResponseEntity<Unit> {
        val member = memberRepository.findById(1).get()

        val pet = petService.findPet(member.petId)
        petService.editPet(pet, petEditDto, imageFile)
        return ResponseEntity
                .status(HttpStatus.OK).build()
    }
}