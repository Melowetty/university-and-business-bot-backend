package ru.sigma.hse.business.bot.exception.base

open class BadArgumentException(
    errorType: String,
    errorMessage: String
) : BaseException(errorType, errorMessage)