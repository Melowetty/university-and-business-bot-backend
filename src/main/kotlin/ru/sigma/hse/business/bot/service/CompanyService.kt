package ru.sigma.hse.business.bot.service

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import ru.sigma.hse.business.bot.api.controller.model.CreateCompanyRequest
import ru.sigma.hse.business.bot.domain.event.CreatedVisitableObjectEvent
import ru.sigma.hse.business.bot.domain.model.Company
import ru.sigma.hse.business.bot.exception.company.CompanyByIdNotFoundException
import ru.sigma.hse.business.bot.persistence.CompanyStorage
import ru.sigma.hse.business.bot.service.code.CodeGenerator

@Service
class CompanyService(
    private val codeGenerator: CodeGenerator,
    private val companyStorage: CompanyStorage,
    private val eventPublisher: ApplicationEventPublisher,
) {
    fun createCompany(request: CreateCompanyRequest): Company {
        val code = codeGenerator.generateCompanyCode()
        val company = companyStorage.createCompany(
            code = code,
            name = request.name,
            description = request.description,
            vacanciesLink = request.vacanciesLink
        )

        eventPublisher.publishEvent(CreatedVisitableObjectEvent(company, code))
        return company
    }

    fun getCompany(companyId: Long): Company {
        return companyStorage.getCompany(companyId)
            ?: throw CompanyByIdNotFoundException(companyId)
    }
}