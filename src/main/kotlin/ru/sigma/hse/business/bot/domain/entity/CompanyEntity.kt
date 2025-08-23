package ru.sigma.hse.business.bot.domain.entity

data class CompanyEntity(
    val id: Long,
    val code: String,
    val name: String,
    val description: String,
    val vacanciesLink: String
)
