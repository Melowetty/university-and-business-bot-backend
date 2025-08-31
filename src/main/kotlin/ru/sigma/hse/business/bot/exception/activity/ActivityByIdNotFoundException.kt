package ru.sigma.hse.business.bot.exception.activity

import ru.sigma.hse.business.bot.exception.base.NotFoundException

class ActivityByIdNotFoundException(
    activityId: Long,
) : NotFoundException(
    "Activity",
    "Activity with id $activityId not found"
)