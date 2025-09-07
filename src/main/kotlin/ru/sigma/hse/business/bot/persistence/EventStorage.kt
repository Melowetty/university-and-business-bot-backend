package ru.sigma.hse.business.bot.persistence

import ru.sigma.hse.business.bot.domain.model.Event
import ru.sigma.hse.business.bot.domain.model.EventStatus

interface EventStorage {
    fun getEvent(id: Long): Event?

    fun createEvent(
        answers: List<String>,
        rightAnswer: String?
    ): Event

    fun updateEventStatus(
        id: Long,
        status: EventStatus
    ): Event

    fun deleteEvent(id: Long)
}