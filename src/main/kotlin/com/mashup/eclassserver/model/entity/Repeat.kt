package com.mashup.eclassserver.model.entity

import javax.persistence.*

@Entity
data class Repeat(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val repeatId: Long = 0,

    @Enumerated(EnumType.STRING)
    val repeatType: RepeatType,

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "schedule_id")
    val schedule: Schedule
) : BaseEntity()

enum class RepeatType {
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY
}