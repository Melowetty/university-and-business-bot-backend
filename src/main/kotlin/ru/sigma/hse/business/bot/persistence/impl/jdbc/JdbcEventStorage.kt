package ru.sigma.hse.business.bot.persistence.impl.jdbc

import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.LocalTime
import org.springframework.stereotype.Component
import ru.sigma.hse.business.bot.domain.entity.EventEntity
import ru.sigma.hse.business.bot.domain.model.Event
import ru.sigma.hse.business.bot.domain.model.EventStatus
import ru.sigma.hse.business.bot.persistence.repository.EventRepository

@Component
class JdbcEventStorage(
    private val eventRepository: EventRepository
) {
    fun getEvent(id: Long): Event? {
        if (eventRepository.existsById(id)) {
            logger.info { "Found event with id $id" }
            return eventRepository.findById(id).get().toEvent()
        }

        logger.warn { "Event with id $id does not exist" }
        return null
    }

    fun createEvent(
        answers: List<String>
    ): Event {
        val eventEntity = EventEntity(
            status = EventStatus.PREPARED,
            answers = answers
        )

        val savedEntity = eventRepository.save(eventEntity)
        logger.info { "Created event with id ${savedEntity.id()}" }
        return savedEntity.toEvent()
    }

    fun updateEventStatus(
        id: Long,
        status: EventStatus
    ): Event {
        if (!eventRepository.existsById(id)) {
            logger.warn { "Event with id ${id} does not exist" }
            throw NoSuchElementException("Event with id ${id} does not exist")
        }

        val existingEvent = eventRepository.findById(id).get()
        val eventEntity = existingEvent.apply {
            this.status = status
        }

        val savedEntity = eventRepository.save(eventEntity)
        logger.info { "Updated event with id ${eventEntity.id()}" }
        return savedEntity.toEvent()
    }

    fun deleteEvent(id: Long) {
        if (!eventRepository.existsById(id)) {
            logger.warn { "Event with id $id does not exist" }
            throw NoSuchElementException("Event with id $id does not exist")
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
                answers = answers
            )
        }
    }
}