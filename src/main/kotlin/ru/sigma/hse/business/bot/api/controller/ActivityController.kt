package ru.sigma.hse.business.bot.api.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.sigma.hse.business.bot.api.controller.model.NewActivityRequest
import ru.sigma.hse.business.bot.service.CompanyService

@RestController
@RequestMapping("/activity")
class ActivityController(
    private val visitService: CompanyService
) {
    @PostMapping("/create")
    fun visitCompany(@RequestBody newActivity: NewActivityRequest): String {
        return "companyService.(id)"
    }
}