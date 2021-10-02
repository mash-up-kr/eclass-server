package com.mashup.eclassserver.model.repository

import com.mashup.eclassserver.model.entity.Badge
import org.springframework.data.jpa.repository.JpaRepository

interface BadgeRepository : JpaRepository<Badge, Long> {
}