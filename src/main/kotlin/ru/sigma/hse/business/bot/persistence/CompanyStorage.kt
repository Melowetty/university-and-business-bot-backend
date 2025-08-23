package ru.sigma.hse.business.bot.persistence

import ru.sigma.hse.business.bot.domain.model.Company

interface CompanyStorage {
    fun getCompany(id: Long): Company?

    fun createCompany(
        name: String,
        description: String,
        vacanciesLink: String
    ): Company

    fun updateCompany(
        company: Company
    ): Company

    fun deleteCompany(id: Long)
}