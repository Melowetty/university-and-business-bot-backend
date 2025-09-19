package ru.sigma.hse.business.bot.domain.model

import java.time.LocalDateTime

data class Visit(
    var id: Long,
    var userId: Long,
    var targetId: Long,
    var targetType: VisitTarget,
    var time: LocalDateTime,
    var isGotExtraReward: Boolean,
)
