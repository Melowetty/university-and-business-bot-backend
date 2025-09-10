package ru.sigma.hse.business.bot.persistence

import java.time.Duration
import ru.sigma.hse.business.bot.domain.model.ActivityEvent
import ru.sigma.hse.business.bot.domain.model.EventStatus

interface EventStorage {
    fun getEvent(activityId: Long): ActivityEvent?

    fun createEvent(
        activityId: Long,
        name: String,
        description: String,
        duration: Duration?,
        answers: List<String>,
        rightAnswer: String?,
        reward: Int
    ): ActivityEvent

    fun updateEventStatus(
        activityId: Long,
        status: EventStatus
    ): ActivityEvent

    fun deleteEvent(activityId: Long)
}