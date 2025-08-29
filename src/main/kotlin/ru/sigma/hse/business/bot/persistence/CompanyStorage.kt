package ru.sigma.hse.business.bot.persistence

import ru.sigma.hse.business.bot.domain.model.Company

interface CompanyStorage {
    fun getCompany(id: Long): Company?

    fun getCompanies(ids: List<Long>): List<Company>

    fun createCompany(
        code: String,
        name: String,
        description: String,
        vacanciesLink: String
    ): Company

    fun updateCompany(
        company: Company
    ): Company

    fun deleteCompany(id: Long)
}