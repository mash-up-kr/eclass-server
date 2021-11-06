package com.mashup.eclassserver.service

import com.mashup.eclassserver.exception.EclassException
import com.mashup.eclassserver.exception.ErrorCode
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
    fun savePet(pet: Pet, imageFile: MultipartFile): Pet {
        val imageUrl = s3Supporter.transmit(imageFile, S3Supporter.COVERS)
        pet.imageUrl = imageUrl.url
        return petRepository.save(pet)
    }

    @Transactional(readOnly = true)
    fun findPet(petId: Long): Pet {
        return petRepository.findByIdOrNull(petId) ?: throw EclassException(ErrorCode.PET_NOT_FOUND)
    }
}