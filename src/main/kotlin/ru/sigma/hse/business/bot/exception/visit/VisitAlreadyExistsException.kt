package ru.sigma.hse.business.bot.exception.visit

import ru.sigma.hse.business.bot.exception.base.AlreadyExistsException

class VisitAlreadyExistsException(
    code: String
) : AlreadyExistsException(
    "Visit",
    "Visit with code $code already registered"
)