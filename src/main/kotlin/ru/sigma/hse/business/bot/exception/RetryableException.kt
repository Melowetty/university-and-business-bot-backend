package ru.sigma.hse.business.bot.exception

import java.lang.RuntimeException

class RetryableException(throwable: Throwable) : RuntimeException(throwable)