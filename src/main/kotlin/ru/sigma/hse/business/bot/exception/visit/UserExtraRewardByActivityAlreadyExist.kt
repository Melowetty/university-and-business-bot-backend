package ru.sigma.hse.business.bot.exception.visit

import ru.sigma.hse.business.bot.exception.base.AlreadyExistsException

class UserExtraRewardByActivityAlreadyExist(
    userId: Long,
    activityId: Long
): AlreadyExistsException("Visit","User $userId already got extra reward in activity with id $activityId"
)