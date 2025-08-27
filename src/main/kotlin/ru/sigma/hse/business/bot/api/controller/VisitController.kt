package ru.sigma.hse.business.bot.api.controller

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.sigma.hse.business.bot.service.VisitService


@RestController
@RequestMapping("/visit")
class VisitController(
    private val visitService: VisitService
) {
    @PostMapping("/{id}")
    fun scanCompany(@PathVariable id: String): String {
        return visitService.visit(id)
    }
}