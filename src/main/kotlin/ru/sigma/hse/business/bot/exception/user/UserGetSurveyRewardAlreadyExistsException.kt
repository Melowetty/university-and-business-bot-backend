package ru.sigma.hse.business.bot.exception.user

import ru.sigma.hse.business.bot.exception.base.AlreadyExistsException

class UserGetSurveyRewardAlreadyExistsException(
    id: Long,
) : AlreadyExistsException("User", "User with id $id already got survey reward")