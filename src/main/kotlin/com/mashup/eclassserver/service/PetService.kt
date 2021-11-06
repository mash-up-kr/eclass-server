package com.mashup.eclassserver.service

import com.mashup.eclassserver.exception.EclassException
import com.mashup.eclassserver.exception.ErrorCode
import com.mashup.eclassserver.model.dto.PetEditDto
import com.mashup.eclassserver.model.entity.Pet
import com.mashup.eclassserver.model.repository.PetRepository
import com.mashup.eclassserver.supporter.S3Supporter
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class PetService(
    private val petRepository: PetRepository,
    private val s3Supporter: S3Supporter
) {
    @Transactional
    fun savePet(pet: Pet, imageFile: MultipartFile?): Pet {
        imageFile?.let {
            val imageUrl = s3Supporter.transmit(imageFile, S3Supporter.PETS)
            pet.imageUrl = imageUrl.url
        }
        return petRepository.save(pet)
    }

    @Transactional(readOnly = true)
    fun findPet(petId: Long): Pet {
        return petRepository.findByIdOrNull(petId) ?: throw EclassException(ErrorCode.PET_NOT_FOUND)
    }

    @Transactional
    fun editPet(pet: Pet, editDto: PetEditDto, imageFile: MultipartFile?): Pet {
        editDto.birthDate?.let { pet.birthDate = editDto.birthDate }
        editDto.name?.let { pet.name = editDto.name }
        imageFile?.let {
            val imageUrl = s3Supporter.transmit(imageFile, S3Supporter.PETS)
            pet.imageUrl = imageUrl.url
        }
        return petRepository.save(pet)
    }
}