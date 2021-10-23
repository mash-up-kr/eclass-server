package com.mashup.eclassserver.model.repository

import com.mashup.eclassserver.model.entity.Reply
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ReplyRepository : JpaRepository<Reply, Long> {
    fun findByDiaryIdAndReplyId(diaryId: Long, replyId: Long): Optional<Reply>
    fun deleteByDiaryIdAndReplyId(diaryId: Long, replyId: Long)
    fun findAllByDiaryId(diaryId: Long): List<Reply>
}