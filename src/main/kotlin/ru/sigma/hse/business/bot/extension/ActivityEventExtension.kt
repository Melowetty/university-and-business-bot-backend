package ru.sigma.hse.business.bot.extension

import ru.sigma.hse.business.bot.api.controller.model.ActivityEventRequest
import ru.sigma.hse.business.bot.domain.model.ActivityEvent


fun ActivityEvent.toDto(): ActivityEventRequest {
    return ActivityEventRequest(
        id = id,
        name = name,
        description = description,
        status = status,
        answers = answers,
        duration = duration
    )
}