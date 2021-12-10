package com.mashup.eclassserver.controller

import com.mashup.eclassserver.config.LoginInfo
import com.mashup.eclassserver.model.dto.*
import com.mashup.eclassserver.service.BadgeService
import com.mashup.eclassserver.service.DiaryService
import com.mashup.eclassserver.service.MemberService
import com.mashup.eclassserver.service.ReplyService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/diary")
class DiaryController(
    private val diaryService: DiaryService,
    private val memberService: MemberService,
    private val replyService: ReplyService,
    private val badgeService: BadgeService
) {
    @PostMapping
    fun submitDiary(@RequestBody diaryDto: DiaryRequestDto, @LoginInfo loginInfo: LoginInfoWithPetDto): ResponseEntity<Unit> {
        val member = memberService.findById(loginInfo.memberId)

        val diary = diaryService.submitDiary(diaryDto, member)
        diaryDto.badgeId?.let {
            val badge = badgeService.findBadgeById(diaryDto.badgeId)
            diaryService.saveBadge(diaryDto.badgeId, badge)
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null)
    }

    @GetMapping("/list")
    fun getDiary(
        @RequestParam(name = "year") year: Int,
        @RequestParam(name = "month") month: Int,
        @LoginInfo loginInfo: LoginInfoWithPetDto
    ): ResponseEntity<List<DiaryResponseDto>> {
        val member = memberService.findById(loginInfo.memberId)
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(diaryService.getDiaryListByDate(member, year, month))
    }

    @GetMapping
    fun getDiaryId(@LoginInfo loginInfo: LoginInfoWithPetDto): ResponseEntity<List<Long>> {
        val member = memberService.findById(loginInfo.memberId)

        val resultList = diaryService.getDiaryIdList(member)
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(resultList)
    }

    @GetMapping("/{diaryId}")
    fun getDiaryById(@PathVariable diaryId: Long): ResponseEntity<DiaryResponseDto> {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(diaryService.findDiaryById(diaryId))
    }

    @GetMapping("/{diaryId}/reply")
    fun getDiaryReplyList(@PathVariable diaryId: Long): ResponseEntity<*> {
        val replyResponse = replyService.getReplyList(diaryId)

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(replyResponse)
    }

    @PostMapping("/{diaryId}/reply")
    fun registerReply(
        @PathVariable diaryId: Long,
        @RequestBody replyRegisterRequest: ReplyRegisterRequest,
        @LoginInfo loginInfo: LoginInfoWithPetDto
    ): ResponseEntity<*> {
        val member = memberService.findById(loginInfo.memberId)

        replyService.registerReply(diaryId, replyRegisterRequest, member)
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null)
    }

    @PutMapping("/{diaryId}/reply/{replyId}")
    fun editReply(@PathVariable diaryId: Long, @PathVariable replyId: Long, @RequestBody replyEditRequest: ReplyEditRequest): ResponseEntity<*> {
        replyService.editReply(diaryId, replyId, replyEditRequest)
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null)
    }

    @DeleteMapping("/{diaryId}/reply/{replyId}")
    fun deleteReply(@PathVariable diaryId: Long, @PathVariable replyId: Long): ResponseEntity<*> {
        replyService.deleteReply(diaryId, replyId)
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null)
    }
}