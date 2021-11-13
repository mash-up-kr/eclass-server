package com.mashup.eclassserver.model.entity

import com.mashup.eclassserver.model.dto.ScheduleRequestDto
import javax.persistence.*

@Entity
data class Repeat(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val repeatId: Long = 0,

    val petId: Long = 0,

    @Enumerated(EnumType.STRING)
    val repeatType: RepeatType,

    @OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "schedule_id")
    val schedule: Schedule
) : BaseEntity() {
    companion object {
        fun of(scheduleRequestDto: ScheduleRequestDto, member: Member): Repeat =
                Repeat(
                    petId = member.petId,
                    repeatType = scheduleRequestDto.repeatType!!,
                    schedule = Schedule.of(scheduleRequestDto, member)
                )
    }
}

enum class RepeatType {
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY
}