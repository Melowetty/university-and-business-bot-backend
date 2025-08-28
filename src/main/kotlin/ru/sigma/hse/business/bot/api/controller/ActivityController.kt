package ru.sigma.hse.business.bot.api.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.sigma.hse.business.bot.api.controller.model.CreateActivityRequest
import ru.sigma.hse.business.bot.service.CompanyService

@RestController
@RequestMapping("/activities")
class ActivityController(
    private val visitService: CompanyService
) {
    @PostMapping()
    fun createActivity(@RequestBody newActivity: CreateActivityRequest): String {
        return "companyService.(id)"
    }
}