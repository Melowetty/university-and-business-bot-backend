package ru.sigma.hse.business.bot.exception.base

abstract class BaseException(
    val errorType: String,
    override val message: String
) : RuntimeException(message)