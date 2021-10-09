package com.mashup.eclassserver.service

import com.mashup.eclassserver.model.dto.EditReplyRequest
import com.mashup.eclassserver.model.dto.RegisterReplyRequest
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

    fun registerReply(diaryId: Long, registerReplyRequest: RegisterReplyRequest, member: Member) {
        val reply = Reply.of(diaryId, registerReplyRequest, member)
        replyRepository.save(reply)
    }

    fun editReply(diaryId: Long, replyId: Long, editReplyRequest: EditReplyRequest) {
        val reply = replyRepository.findByDiaryIdAndReplyId(diaryId, replyId)
                .orElseThrow() // Todo: 없는 경우 예외 던지기, 예외 형식 만들어서?
        reply.content = editReplyRequest.content
        replyRepository.save(reply)
    }

    fun deleteReply(diaryId:Long, replyId: Long) {
        replyRepository.deleteByDiaryIdAndReplyId(diaryId, replyId)
    }

}
