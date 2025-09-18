package ru.sigma.hse.business.bot.extension

import ru.sigma.hse.business.bot.api.controller.model.ActivityRequest
import ru.sigma.hse.business.bot.domain.model.Activity


fun Activity.toDto(): ActivityRequest {
    return ActivityRequest(
        id = id,
        name = name,
        description = description,
        activityType = activityType,
        location = location,
        startTime = startTime,
        endTime = endTime,
    )
}