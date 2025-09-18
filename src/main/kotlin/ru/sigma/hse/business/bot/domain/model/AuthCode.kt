package ru.sigma.hse.business.bot.domain.model

data class AuthCode(
    val code: String,
    val role: UserRole
)
