package ru.sigma.hse.business.bot.domain.model

sealed class Visitable(
    open val id: Long,
    open val name: String,
    open val description: String,
)