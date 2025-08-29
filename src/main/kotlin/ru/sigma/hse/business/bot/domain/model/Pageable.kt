package ru.sigma.hse.business.bot.domain.model

data class Pageable<T>(
    val data: List<T>,
    val nextToken: Long,
)
