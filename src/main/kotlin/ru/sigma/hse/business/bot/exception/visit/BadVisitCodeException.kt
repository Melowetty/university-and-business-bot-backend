package ru.sigma.hse.business.bot.exception.visit

import ru.sigma.hse.business.bot.exception.base.BadArgumentException

class BadVisitCodeException : BadArgumentException(
    "BAD_VISIT_CODE",
    "Неверный формат кода посещения"
)