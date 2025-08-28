package ru.sigma.hse.business.bot.domain.model

data class Company(
    override val id: Long,
    override val name: String,
    override val description: String,
    val vacanciesLink: String
) : Visitable(id, name, description)
