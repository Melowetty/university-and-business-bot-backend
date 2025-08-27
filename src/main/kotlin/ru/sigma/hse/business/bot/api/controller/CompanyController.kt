package ru.sigma.hse.business.bot.api.controller

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.sigma.hse.business.bot.api.controller.model.NewCompanyRequest
import ru.sigma.hse.business.bot.service.CompanyService

@RestController
@RequestMapping("/company")
class CompanyController(
    private val companyService: CompanyService
) {


    @PostMapping("/create")
    fun getQrCode(@RequestBody newCompany: NewCompanyRequest): String {
        return companyService.createCompany(newCompany)
    }


}
