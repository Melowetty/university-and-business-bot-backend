package ru.sigma.hse.business.bot.api.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.sigma.hse.business.bot.domain.model.DetailedVisit
import ru.sigma.hse.business.bot.service.VisitService

@RestController
@RequestMapping("/users/{userId}/visits")
class VisitController(
    private val visitService: VisitService
) {
    @PostMapping("/{code}")
    fun visitFlow(@PathVariable("userId") userId: Long, @PathVariable("code") code: String): DetailedVisit {
        return visitService.visit(userId, code)
    }

    @GetMapping
    fun getUserVisits(@PathVariable("userId") userId: Long): List<DetailedVisit> {
        return visitService.getUserVisits(userId)
    }
}