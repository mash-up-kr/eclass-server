package com.mashup.eclassserver.controller

import com.mashup.eclassserver.model.dto.DiaryDto
import com.mashup.eclassserver.model.dto.ReplyEditRequest
import com.mashup.eclassserver.model.dto.ReplyRegisterRequest
import com.mashup.eclassserver.model.entity.Member
import com.mashup.eclassserver.model.repository.MemberRepository
import com.mashup.eclassserver.service.DiaryService
import com.mashup.eclassserver.service.ReplyService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/diary")
class DiaryController(
    private val diaryService: DiaryService,
    private val memberRepository: MemberRepository,
    private val replyService: ReplyService
) {
    @PostMapping
    fun submitDiary(@RequestBody diaryDto: DiaryDto): ResponseEntity<*> {
        val member = memberRepository.findById(1).get()

        diaryService.submitDiary(diaryDto, member)
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null)
    }

    @GetMapping
    fun getDiary(): ResponseEntity<*> {
        val member = memberRepository.findById(1).get()

        val resultList = diaryService.getDiaryList(member)
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(resultList)
    }

    @GetMapping("/{diaryId}/reply/list")
    fun getDiaryReplyList(@PathVariable(value = "diaryId") diaryId: Long): ResponseEntity<*> {
        val replyResponse = replyService.getReplyList(diaryId)

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(replyResponse)
    }

    @PostMapping("/{diaryId}/reply/register")
    fun registerReply(@PathVariable(value = "diaryId") diaryId: Long, @RequestBody replyRegisterRequest: ReplyRegisterRequest): ResponseEntity<*> {
        val member = Member(1L, 1L, "eclass") // dummy

        replyService.registerReply(diaryId, replyRegisterRequest, member)
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null)
    }

    @PutMapping("/{diaryId}/reply/edit/{replyId}")
    fun editReply(@PathVariable(value = "diaryId") diaryId: Long, @PathVariable(value = "replyId") replyId: Long, @RequestBody replyEditRequest: ReplyEditRequest): ResponseEntity<*> {
        replyService.editReply(diaryId, replyId, replyEditRequest)
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