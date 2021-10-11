package com.mashup.eclassserver.service

import com.mashup.eclassserver.model.dto.ReplyEditRequest
import com.mashup.eclassserver.model.dto.ReplyRegisterRequest
import com.mashup.eclassserver.model.dto.ReplyResponse
import com.mashup.eclassserver.model.dto.ReplyResponseDto
import com.mashup.eclassserver.model.entity.Member
import com.mashup.eclassserver.model.entity.Reply
import com.mashup.eclassserver.model.repository.ReplyRepository
import org.springframework.stereotype.Service

@Service
class ReplyService(
    private val replyRepository: ReplyRepository
) {
    fun getReplyList(diaryId: Long): ReplyResponse {
        return ReplyResponse(replyRepository.findAllByDiaryId(diaryId)
                .map { reply ->  ReplyResponseDto.of(reply) }
                .toList()
        )
    }

    fun registerReply(diaryId: Long, replyRegisterRequest: ReplyRegisterRequest, member: Member) {
        val reply = Reply.of(diaryId, replyRegisterRequest, member)
        replyRepository.save(reply)
    }

    fun editReply(diaryId: Long, replyId: Long, replyEditRequest: ReplyEditRequest) {
        val reply = replyRepository.findByDiaryIdAndReplyId(diaryId, replyId)
                .orElseThrow() // Todo: 없는 경우 예외 던지기, 예외 형식 만들어서?
        reply.content = replyEditRequest.content
        replyRepository.save(reply)
    }

    fun deleteReply(diaryId:Long, replyId: Long) {
        replyRepository.deleteByDiaryIdAndReplyId(diaryId, replyId)
    }

}
