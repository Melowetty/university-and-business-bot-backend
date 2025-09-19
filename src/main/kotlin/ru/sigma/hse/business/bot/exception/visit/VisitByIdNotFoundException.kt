package ru.sigma.hse.business.bot.exception.visit

import ru.sigma.hse.business.bot.exception.base.NotFoundException

class VisitByIdNotFoundException(
    id: Long,
): NotFoundException ("Visit","Visit with id $id not found")