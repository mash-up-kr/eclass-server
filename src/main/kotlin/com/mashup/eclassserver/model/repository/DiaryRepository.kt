package com.mashup.eclassserver.model.repository

import com.mashup.eclassserver.model.entity.Diary
import com.mashup.eclassserver.model.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface DiaryRepository : JpaRepository<Diary, Long> {
    fun findAllByMember(member: Member): List<Diary>
}