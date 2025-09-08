package ru.sigma.hse.business.bot.persistence.impl.jdbc

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.transaction.Transactional
import kotlin.jvm.optionals.getOrNull
import org.springframework.stereotype.Component
import ru.sigma.hse.business.bot.domain.entity.EventEntity
import ru.sigma.hse.business.bot.domain.model.Event
import ru.sigma.hse.business.bot.domain.model.EventStatus
import ru.sigma.hse.business.bot.exception.event.EventByIdNotFoundException
import ru.sigma.hse.business.bot.persistence.repository.EventRepository

@Component
class JdbcEventStorage(
    private val eventRepository: EventRepository
) {
    fun getEvent(id: Long): Event? {
        val event = eventRepository.findById(id).getOrNull()
            ?: run {
                logger.warn { "Event with id $id does not exist" }
                return null
            }

        logger.info { "Found event with id $id" }
        return event.toEvent()
    }

    fun createEvent(
        answers: List<String>,
        rightAnswer: String?
    ): Event {
        val eventEntity = EventEntity(
            status = EventStatus.PREPARED,
            answers = answers,
            rightAnswer = rightAnswer
        )

        val savedEntity = eventRepository.save(eventEntity)
        logger.info { "Created event with id ${savedEntity.id()}" }
        return savedEntity.toEvent()
    }

    @Transactional
    fun updateEventStatus(
        id: Long,
        status: EventStatus
    ): Event {
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

        private fun EventEntity.toEvent(): Event {
            return Event(
                id = this.id(),
                status = status,
                answers = answers,
                rightAnswer = rightAnswer
            )
        }
    }
}