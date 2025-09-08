package ru.sigma.hse.business.bot.persistence.impl.jdbc

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlin.jvm.optionals.getOrNull
import org.springframework.stereotype.Component
import ru.sigma.hse.business.bot.domain.entity.CompanyEntity
import ru.sigma.hse.business.bot.domain.model.Company
import ru.sigma.hse.business.bot.exception.company.CompanyByIdNotFoundException
import ru.sigma.hse.business.bot.persistence.repository.CompanyRepository

@Component
class JdbcCompanyStorage(
    private val companyRepository: CompanyRepository
) {
    fun getCompany(id: Long): Company? {
        val company = companyRepository.findById(id).getOrNull()
            ?: run {
                logger.warn { "Company with id $id does not exist" }
                return null
            }

        logger.info { "Found company with id $id" }
        return company.toCompany()
    }

    fun getCompanies(ids: List<Long>): List<Company> {
        if (ids.isEmpty()) {
            return emptyList()
        }

        return companyRepository.findAllById(ids).map { it.toCompany() }
    }

    fun createCompany(
        code: String,
        name: String,
        description: String,
        vacanciesLink: String
    ): Company {
        val entity = companyRepository.save(
            CompanyEntity(
                code = code,
                name = name,
                description = description,
                vacanciesLink = vacanciesLink
            )
        )

        logger.info { "Created company with id ${entity.id()}" }
        return entity.toCompany()
    }

    fun updateCompany(
        company: Company
    ): Company {
        if (!companyRepository.existsById(company.id)) {
            logger.warn { "Company with id ${company.id} does not exist" }
            throw CompanyByIdNotFoundException(company.id)
        }

        val existingCompany = companyRepository.findById(company.id).get()
        val updatedCompany = existingCompany.apply {
            name = company.name
            description = company.description
            vacanciesLink = company.vacanciesLink
        }

        logger.info { "Updated company with id ${updatedCompany.id()}" }
        return companyRepository.save(updatedCompany).toCompany()
    }

    fun deleteCompany(id: Long) {
        if (!companyRepository.existsById(id)) {
            logger.warn { "Company with id $id does not exist" }
            return
        }

        logger.info { "Deleting company with id $id" }
        companyRepository.deleteById(id)
    }

    fun findByCode(code: String): Company? {
        val entity = companyRepository.findByCode(code)
        return entity?.toCompany()
    }

    companion object {
        private val logger = KotlinLogging.logger { }

        private fun CompanyEntity.toCompany(): Company {
            return Company(
                id = this.id(),
                name = this.name,
                description = this.description,
                vacanciesLink = this.vacanciesLink
            )
        }
    }
}