package ru.sigma.hse.business.bot.exception.base

abstract class NotFoundException(
    entityName: String,
    override val message: String,
) : BaseException(
    errorType = "${entityName.uppercase()}_NOT_FOUND",
    message = message,
)