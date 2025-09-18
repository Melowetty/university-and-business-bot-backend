package ru.sigma.hse.business.bot.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import ru.sigma.hse.business.bot.api.controller.model.CreateActivityRequest
import ru.sigma.hse.business.bot.domain.event.CreatedVisitableObjectEvent
import ru.sigma.hse.business.bot.domain.model.Activity
import ru.sigma.hse.business.bot.exception.activity.ActivityByIdNotFoundException
import ru.sigma.hse.business.bot.persistence.ActivityStorage
import ru.sigma.hse.business.bot.service.code.CodeGenerator

@Service
class ActivityService(
    private val codeGenerator: CodeGenerator,
    private val activityStorage: ActivityStorage,
    private val eventPublisher: ApplicationEventPublisher,
) {
    fun createActivity(request: CreateActivityRequest): Activity {
        val code = codeGenerator.generateActivityCode()
        val activity = activityStorage.createActivity(
            code = code,
            name = request.name,
            description = request.description,
            type = request.type,
            location = request.location,
            startTime = request.startTime,
            endTime = request.endTime,
            keyWord = request.keyWord,
            points = request.points
        )

        eventPublisher.publishEvent(CreatedVisitableObjectEvent(activity, code))
        logger.info { "Activity with id ${activity.id} created" }
        return activity
    }

    fun getActivity(activityId: Long): Activity {
        val activity = activityStorage.getActivity(activityId)
            ?: throw ActivityByIdNotFoundException(activityId)
        return activity
    }

    fun getAllActivities(): List<Activity> {
        return activityStorage.getAllActivities()
    }

    companion object {
        private val logger = KotlinLogging.logger {  }
    }
}