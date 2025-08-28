package ru.sigma.hse.business.bot.domain.event

import ru.sigma.hse.business.bot.domain.model.Visitable

data class CreatedVisitableObjectEvent(
    val data: Visitable,
    val code: String,
)
