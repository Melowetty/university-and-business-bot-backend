package ru.sigma.hse.business.bot.exception.event

import ru.sigma.hse.business.bot.exception.base.BadArgumentException

class BadEventStateException(
    message: String
) : BadArgumentException("BAD_EVENT_STATE", message)