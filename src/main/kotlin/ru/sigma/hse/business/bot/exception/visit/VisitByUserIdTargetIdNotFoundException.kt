package ru.sigma.hse.business.bot.exception.visit

import ru.sigma.hse.business.bot.exception.base.NotFoundException

class VisitByUserIdTargetIdNotFoundException(
    userId: Long,
    targetId: Long
): NotFoundException ("Visit","User $userId do not visit target $targetId")