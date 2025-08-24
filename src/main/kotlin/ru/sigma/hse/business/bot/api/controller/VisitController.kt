package ru.sigma.hse.business.bot.api.controller

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.sigma.hse.business.bot.service.VisitService

@RestController
@RequestMapping("/company")
class VisitController(
    private val visitService: VisitService
) {
    @PostMapping("start={link}") //https://t.me/company?start=company1
    fun visitCompany(@PathVariable link: String): String {
        return visitService.visitEvent(link)
    }
}
