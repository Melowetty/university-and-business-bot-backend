package ru.sigma.hse.business.bot.domain.model

data class Company(
    val id: Long,
    val name: String,
    val description: String,
    val vacanciesLink: String
)
