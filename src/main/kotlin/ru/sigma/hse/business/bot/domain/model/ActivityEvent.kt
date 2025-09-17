package ru.sigma.hse.business.bot.domain.model

import io.swagger.v3.oas.annotations.media.Schema
import java.time.Duration

data class ActivityEvent (
    val id: Long,
    val name: String,
    val description: String,
    val duration: Duration?,
    var status: EventStatus,
    val answers: List<String>,
    val rightAnswer: String?,
    val reward: Int,
)