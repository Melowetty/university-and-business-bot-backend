package ru.sigma.hse.business.bot.exception.visit

import ru.sigma.hse.business.bot.exception.base.AlreadyExistsException

class VisitAlreadyExistsException: AlreadyExistsException(
    "Visit",
    "Visit already registered"
)