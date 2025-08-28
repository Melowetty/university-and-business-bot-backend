package ru.sigma.hse.business.bot.domain.model

data class DetailedVisit(
    val target: Visitable,
    val type: VisitTarget,
)
