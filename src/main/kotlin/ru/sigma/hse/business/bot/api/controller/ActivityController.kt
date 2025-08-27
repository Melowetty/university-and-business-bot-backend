package ru.sigma.hse.business.bot.api.controller

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.sigma.hse.business.bot.service.CompanyService

@RestController
@RequestMapping("/activity")
class ActivityController(
    private val visitService: CompanyService
) {
    @PostMapping("/{id}")
    fun visitCompany(@PathVariable id: Long): String {
        return "companyService.(id)"
    }
}