package com.mashup.eclassserver.controller

import com.mashup.eclassserver.model.dto.DiarySubmitRequest
import com.mashup.eclassserver.model.dto.EditReplyRequest
import com.mashup.eclassserver.model.dto.RegisterReplyRequest
import com.mashup.eclassserver.model.repository.MemberRepository
import com.mashup.eclassserver.service.DiaryService
import com.mashup.eclassserver.service.ReplyService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping(value = ["/api/v1/diary"])
@RestController
class DiaryController(
    private val diaryService: DiaryService,
    private val memberRepository: MemberRepository,
    private val replyService: ReplyService
) {
    @PostMapping
    fun submitDiary(@RequestBody diarySubmitRequest: DiarySubmitRequest): ResponseEntity<*> {
        val member = memberRepository.findById(1).get() //dummy data

        diaryService.submitDiary(diarySubmitRequest, member)
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null)
    }

    @GetMapping("/{diaryId}/reply/list")
    fun getDiaryReplyList(@PathVariable(value = "diaryId") diaryId: Long): ResponseEntity<*> {
        val replyResponse = replyService.getReplyList(diaryId)

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(replyResponse)
    }

    @PostMapping("/{diaryId}/reply/register")
    fun registerReply(@PathVariable(value = "diaryId") diaryId: Long, @RequestBody registerReplyRequest: RegisterReplyRequest): ResponseEntity<*> {
        val member = memberRepository.findById(2).get() // dummy data

        replyService.registerReply(diaryId, registerReplyRequest, member)
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null)
    }

    @PutMapping("/{diaryId}/reply/edit/{replyId}")
    fun editReply(@PathVariable(value = "diaryId") diaryId: Long, @PathVariable(value = "replyId") replyId: Long, @RequestBody editReplyRequest: EditReplyRequest): ResponseEntity<*> {
        replyService.editReply(diaryId, replyId, editReplyRequest)
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null)
    }

    @DeleteMapping("/{diaryId}/reply/delete/{replyId}")
    fun deleteReply(@PathVariable(value = "diaryId") diaryId: Long, @PathVariable(value = "replyId") replyId: Long): ResponseEntity<*> {
        replyService.deleteReply(diaryId, replyId)
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null)
    }
}