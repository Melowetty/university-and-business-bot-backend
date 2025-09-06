package ru.sigma.hse.business.bot.service

import org.springframework.stereotype.Service
import ru.sigma.hse.business.bot.api.controller.model.CreateEventRequest
import ru.sigma.hse.business.bot.api.controller.model.CreateVoteRequest
import ru.sigma.hse.business.bot.api.controller.model.UpdateEventStatusRequest
import ru.sigma.hse.business.bot.domain.model.Event
import ru.sigma.hse.business.bot.domain.model.Vote
import ru.sigma.hse.business.bot.persistence.EventStorage
import ru.sigma.hse.business.bot.persistence.VoteStorage

@Service
class EventService(
    private val eventStorage: EventStorage,
    private val voteStorage: VoteStorage,
    private val telegramUserService: TelegramUserService
    ) {
    fun createEvent(request: CreateEventRequest): Event {
        return eventStorage.createEvent(
            answers = request.answers
        )
    }

    fun getEvent(eventId: Long): Event {
        return eventStorage.getEvent(eventId)
            ?: throw IllegalArgumentException("Event not found")
    }

    fun updateEventStatus(id: Long, request: UpdateEventStatusRequest): Event {
        return eventStorage.updateEventStatus(id, request.status)
    }

    fun addVote(eventId: Long, request: CreateVoteRequest): Vote {
        val userId = telegramUserService.getUser(request.userTgId).id
        return voteStorage.createVote(eventId, request.answer, userId)
    }
}