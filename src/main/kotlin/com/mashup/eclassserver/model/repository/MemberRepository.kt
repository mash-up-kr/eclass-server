package com.mashup.eclassserver.model.repository

import com.mashup.eclassserver.model.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
}