package com.mashup.eclassserver.model.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Schedule(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val scheduleId: Long = 0,

    val name: String,

    val location: String,

    val color: String,

    val scheduledAt: LocalDateTime,

    val memberId: Long,

    @OneToOne(mappedBy = "schedule", fetch = FetchType.EAGER)
    val repeat: Repeat
)