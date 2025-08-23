package ru.sigma.hse.business.bot.persistence.impl

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import ru.sigma.hse.business.bot.domain.entity.CompanyEntity
import ru.sigma.hse.business.bot.domain.model.Company
import ru.sigma.hse.business.bot.persistence.repository.CompanyRepository
import ru.sigma.hse.business.bot.utils.CodeGenerator

@Component
class JdbcCompanyStorage(
    private val companyRepository: CompanyRepository
) {
    fun getCompany(id: Long): Company? {
        if (!companyRepository.existsById(id)) {
            logger.warn { "Company with id $id does not exist" }
            return null
        }

        return companyRepository.findById(id).get().toCompany()
    }

    fun createCompany(
        name: String,
        description: String,
        vacanciesLink: String
    ): Company {
        val code = CodeGenerator.generateCode()
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
            throw NoSuchElementException("Company with id ${company.id} does not exist")
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