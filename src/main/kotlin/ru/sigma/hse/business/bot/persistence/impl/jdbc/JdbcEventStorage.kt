package ru.sigma.hse.business.bot.persistence.impl.jdbc

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.transaction.Transactional
import java.time.Duration
import kotlin.jvm.optionals.getOrNull
import org.springframework.stereotype.Component
import ru.sigma.hse.business.bot.domain.entity.ActivityEventEntity
import ru.sigma.hse.business.bot.domain.model.ActivityEvent
import ru.sigma.hse.business.bot.domain.model.EventStatus
import ru.sigma.hse.business.bot.exception.event.EventByIdNotFoundException
import ru.sigma.hse.business.bot.persistence.repository.EventRepository

@Component
class JdbcEventStorage(
    private val eventRepository: EventRepository
) {
    fun getEvent(id: Long): ActivityEvent? {
        val event = eventRepository.findById(id).getOrNull()
            ?: run {
                logger.warn { "Event with id $id does not exist" }
                return null
            }

        logger.info { "Found event with id $id" }
        return event.toEvent()
    }

    fun createEvent(
        activityId: Long,
        name: String,
        description: String,
        duration: Duration?,
        answers: List<String>,
        rightAnswer: String?,
        reward: Int,
    ): ActivityEvent {
        val activityEventEntity = ActivityEventEntity(
            activityId = activityId,
            name = name,
            description = description,
            duration = duration,
            status = EventStatus.PREPARED,
            answers = answers,
            rightAnswer = rightAnswer,
            reward = reward
        )

        val savedEntity = eventRepository.save(activityEventEntity)
        logger.info { "Created event for activity id $activityId" }
        return savedEntity.toEvent()
    }


    @Transactional
    fun updateEventStatus(
        id: Long,
        status: EventStatus
    ): ActivityEvent {
        if (!eventRepository.existsById(id)) {
            logger.warn { "Event with id $id does not exist" }
            throw EventByIdNotFoundException(id)
        }

        eventRepository.updateEventStatus(status, id)
        logger.info { "Updated event with id $id" }
        return getEvent(id)!!
    }

    fun deleteEvent(id: Long) {
        if (!eventRepository.existsById(id)) {
            logger.warn { "Event with id $id does not exist" }
            throw EventByIdNotFoundException(id)
        }

        eventRepository.deleteById(id)
        logger.info { "Deleted event with id $id" }
    }
    
    companion object {
        private val logger = KotlinLogging.logger {  }

        private fun ActivityEventEntity.toEvent(): ActivityEvent {
            return ActivityEvent(
                id = activityId,
                name = name,
                description = description,
                status = status,
                answers = answers,
                duration = duration,
                rightAnswer = rightAnswer,
                reward = reward
            )
        }
    }
}