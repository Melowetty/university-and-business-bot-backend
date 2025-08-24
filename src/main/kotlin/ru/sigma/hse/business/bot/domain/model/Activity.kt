package ru.sigma.hse.business.bot.domain.model

import java.time.LocalTime

data class Activity(
    val id: Long,
    var name: String,
    var description: String,
    var location: String,
    var startTime: LocalTime,
    var endTime: LocalTime,
)
