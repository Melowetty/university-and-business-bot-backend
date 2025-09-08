package ru.sigma.hse.business.bot.persistence.impl.jdbc

import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.LocalTime
import kotlin.jvm.optionals.getOrNull
import org.springframework.stereotype.Component
import ru.sigma.hse.business.bot.domain.entity.ActivityEntity
import ru.sigma.hse.business.bot.domain.model.Activity
import ru.sigma.hse.business.bot.exception.activity.ActivityByIdNotFoundException
import ru.sigma.hse.business.bot.persistence.repository.ActivityRepository

@Component
class JdbcActivityStorage(
    private val activityRepository: ActivityRepository,
) {
    fun getActivity(id: Long): Activity? {
        val activity = activityRepository.findById(id).getOrNull()
            ?: run {
                logger.warn { "Activity with id $id does not exist" }
                return null
            }

        logger.info { "Found activity with id $id" }
        return activity.toActivity()
    }

    fun getActivities(ids: List<Long>): List<Activity> {
        if (ids.isEmpty()) {
            return emptyList()
        }

        return activityRepository.findAllById(ids).map { it.toActivity() }
    }

    fun createActivity(
        code: String,
        name: String,
        description: String,
        location: String,
        startTime: LocalTime,
        endTime: LocalTime,
        eventId: Long?,
        keyWord: String?,
        points: Int
    ): Activity {
        val activityEntity = ActivityEntity(
            code = code,
            name = name,
            description = description,
            location = location,
            startTime = startTime,
            endTime = endTime,
            eventId = eventId,
            keyWord = keyWord,
            points = points
        )

        val savedEntity = activityRepository.save(activityEntity)
        logger.info { "Created activity with id ${savedEntity.id()}" }
        return savedEntity.toActivity()
    }

    fun updateActivity(
        activity: Activity
    ): Activity {
        if (!activityRepository.existsById(activity.id)) {
            logger.warn { "Activity with id ${activity.id} does not exist" }
            throw ActivityByIdNotFoundException(activity.id)
        }

        val existingActivity = activityRepository.findById(activity.id).get()
        val activityEntity = existingActivity.apply {
            this.name = activity.name
            this.description = activity.description
            this.location = activity.location
            this.startTime = activity.startTime
            this.endTime = activity.endTime
        }

        val savedEntity = activityRepository.save(activityEntity)
        logger.info { "Updated activity with id ${activityEntity.id()}" }
        return savedEntity.toActivity()
    }

    fun deleteActivity(id: Long) {
        if (!activityRepository.existsById(id)) {
            logger.warn { "Activity with id $id does not exist" }
            throw ActivityByIdNotFoundException(id)
        }

        activityRepository.deleteById(id)
        logger.info { "Deleted activity with id $id" }
    }

    fun findByCode(code: String): Activity? {
        val activityEntity = activityRepository.findByCode(code)
        return activityEntity?.toActivity()
    }

    companion object {
        private val logger = KotlinLogging.logger {  }

        private fun ActivityEntity.toActivity(): Activity {
            return Activity(
                id = this.id(),
                name = this.name,
                description = this.description,
                location = this.location,
                startTime = this.startTime,
                endTime = this.endTime,
                eventId = this.eventId,
                points = this.points
            )
        }
    }
}