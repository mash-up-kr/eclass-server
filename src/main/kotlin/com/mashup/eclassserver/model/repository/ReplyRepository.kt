package com.mashup.eclassserver.model.repository

import com.mashup.eclassserver.model.entity.Reply
import org.springframework.data.jpa.repository.JpaRepository

interface ReplyRepository : JpaRepository<Reply, Long> {
}