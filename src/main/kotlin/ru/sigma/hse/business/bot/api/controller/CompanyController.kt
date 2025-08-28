package ru.sigma.hse.business.bot.api.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.sigma.hse.business.bot.api.controller.model.CreateCompanyRequest
import ru.sigma.hse.business.bot.domain.model.Company
import ru.sigma.hse.business.bot.service.CompanyService

@RestController
@RequestMapping("/companies")
class CompanyController(
    private val companyService: CompanyService
) {
    @PostMapping
    fun createCompany(@RequestBody newCompany: CreateCompanyRequest): Company {
        return companyService.createCompany(newCompany)
    }
}
