package ru.sigma.hse.business.bot.domain.model

import java.time.LocalTime

data class Activity(
    override val id: Long,
    override val name: String,
    override val description: String,
    val activityType: ActivityType,
    var location: String,
    var startTime: LocalTime,
    var endTime: LocalTime,
    val points: Int
) : Visitable(id, name, description)
