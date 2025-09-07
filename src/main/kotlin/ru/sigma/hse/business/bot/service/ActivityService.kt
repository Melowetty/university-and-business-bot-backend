package ru.sigma.hse.business.bot.service

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
            location = request.location,
            startTime = request.startTime,
            endTime = request.endTime,
            eventId = request.eventId,
            keyWord = request.keyWord,
            points = request.points
        )

        eventPublisher.publishEvent(CreatedVisitableObjectEvent(activity, code))
        return activity
    }

    fun getActivity(activityId: Long): Activity {
        return activityStorage.getActivity(activityId)
            ?: throw IllegalArgumentException("Activity not found")

            ?: throw ActivityByIdNotFoundException(activityId)
    }
}