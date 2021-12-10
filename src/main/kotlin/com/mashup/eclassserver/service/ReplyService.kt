package com.mashup.eclassserver.service

import com.mashup.eclassserver.exception.EclassException
import com.mashup.eclassserver.exception.ErrorCode
import com.mashup.eclassserver.model.dto.ReplyEditRequest
import com.mashup.eclassserver.model.dto.ReplyRegisterRequest
import com.mashup.eclassserver.model.dto.ReplyResponse
import com.mashup.eclassserver.model.dto.ReplyResponseDto
import com.mashup.eclassserver.model.entity.Member
import com.mashup.eclassserver.model.entity.Reply
import com.mashup.eclassserver.model.repository.ReplyRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReplyService(
    private val replyRepository: ReplyRepository
) {
    @Transactional(readOnly = true)
    fun getReplyList(diaryId: Long): ReplyResponse {
        return ReplyResponse(replyRepository.findAllByDiaryId(diaryId)
                .map { reply ->  ReplyResponseDto.of(reply) }
                .toList()
        )
    }

    @Transactional
    fun registerReply(diaryId: Long, replyRegisterRequest: ReplyRegisterRequest, member: Member) {
        val reply = Reply.of(diaryId, replyRegisterRequest, member)
        replyRepository.save(reply)
    }

    @Transactional
    fun editReply(diaryId: Long, replyId: Long, replyEditRequest: ReplyEditRequest) {
        val reply = replyRepository.findByDiaryIdAndReplyId(diaryId, replyId)
                ?: throw EclassException(ErrorCode.REPLY_NOT_FOUND)
        reply.content = replyEditRequest.content
        replyRepository.save(reply)
    }

    @Transactional
    fun deleteReply(diaryId:Long, replyId: Long) {
        replyRepository.deleteByDiaryIdAndReplyId(diaryId, replyId)
    }

}
