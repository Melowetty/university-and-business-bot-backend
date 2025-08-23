package ru.sigma.hse.business.bot.domain.entity

data class ActivityEntity(
    val id: Long,
    val code: String,
    val name: String,
    val description: String,
    val location: String,
    val startDate: Long,
    val endDate: Long
)