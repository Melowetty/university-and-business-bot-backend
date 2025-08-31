package ru.sigma.hse.business.bot.exception.base

abstract class AlreadyExistsException (
    entityName: String,
    override val message: String,
) : BaseException(
    errorType = "${entityName.uppercase()}_ALREADY_EXISTS",
    message = message,
)