package ru.sigma.hse.business.bot.domain.model

import java.time.LocalDateTime

data class UserVisit(
    val userId: Long,
    val target: Visitable,
    val time: LocalDateTime,
)
