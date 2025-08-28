package ru.sigma.hse.business.bot.api.controller.model

data class CreateCompanyRequest(
    val name: String,
    val description: String,
    val vacanciesLink: String
)