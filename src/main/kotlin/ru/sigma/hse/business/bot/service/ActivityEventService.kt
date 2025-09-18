package ru.sigma.hse.business.bot.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.sigma.hse.business.bot.api.controller.model.CreateEventRequest
import ru.sigma.hse.business.bot.domain.model.ActivityEvent
import ru.sigma.hse.business.bot.domain.model.EventStatus
import ru.sigma.hse.business.bot.exception.base.BadArgumentException
import ru.sigma.hse.business.bot.exception.event.BadEventStateException
import ru.sigma.hse.business.bot.exception.event.EventByIdNotFoundException
import ru.sigma.hse.business.bot.job.DisableActivityEventJob
import ru.sigma.hse.business.bot.job.SendNotificationsForActivityEventJob
import ru.sigma.hse.business.bot.persistence.EventStorage

@Service
class ActivityEventService(
    private val eventStorage: EventStorage,
    private val scheduleJobService: ScheduleJobService,
) {
    fun createEvent(activityId: Long, request: CreateEventRequest): ActivityEvent {
        return eventStorage.createEvent(
            activityId = activityId,
            name = request.name,
            description = request.description,
            duration = request.duration,
            answers = request.answers,
            rightAnswer = request.rightAnswer,
            reward = request.reward,
        )
    }

    @Transactional
    fun runEvent(activityId: Long) {
        val event = getEventOrThrow(activityId)

        if (event.status != EventStatus.PREPARED) {
            throw BadEventStateException("Event is already running or ended")
        }
        eventStorage.updateEventStatus(activityId, EventStatus.CONTINUED)

        val payload = mapOf(
            "activityId" to activityId.toString(),
        )

        scheduleJobService.runImmediatelyJob(payload, SendNotificationsForActivityEventJob::class.java)
        event.duration?.let {
            scheduleJobService.scheduleJob(event.duration, payload, DisableActivityEventJob::class.java)
        }

        logger.info { "Event $activityId run" }
    }

    @Transactional
    fun endEvent(activityId: Long) {
        val event = getEventOrThrow(activityId)

        if (event.status != EventStatus.CONTINUED) {
            throw BadEventStateException("Event is not running")
        }

        eventStorage.updateEventStatus(activityId, EventStatus.ENDED)
        logger.info { "Event $activityId ended" }
    }

    fun isCorrectAnswer(activityId: Long, answer: String): Boolean {
        val event = eventStorage.getEvent(activityId)
            ?: throw EventByIdNotFoundException(activityId)
        if (event.rightAnswer == null) {
            return true
        }
        if (event.rightAnswer.uppercase().equals(answer.uppercase())) {
            return true
        }
        return false
    }

    private fun getEventOrThrow(activityId: Long): ActivityEvent {
        return eventStorage.getEvent(activityId)
            ?: throw BadArgumentException("BAD_ACTIVITY", "Activity has no event")
    }

    fun getEvent(activityId: Long): ActivityEvent {
        val event = eventStorage.getEvent(activityId)
            ?: throw EventByIdNotFoundException(activityId)
        return event
    }

    companion object {
        private val logger = KotlinLogging.logger {  }
    }
}