package ru.sigma.hse.business.bot.api.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.sigma.hse.business.bot.domain.model.DetailedVisit
import ru.sigma.hse.business.bot.service.TelegramUserService
import ru.sigma.hse.business.bot.service.VisitService

@RestController
@RequestMapping("/users/{telegramUserId}/visits")
class VisitController(
    private val visitService: VisitService,
    private val telegramUserService: TelegramUserService
) {
    @PostMapping("/{code}")
    fun visitFlow(@PathVariable("telegramUserId") telegramUserId: Long, @PathVariable("code") code: String): DetailedVisit {
        val userId = telegramUserService.getUser(telegramUserId).id
        return visitService.visit(userId, code)
    }

    @GetMapping
    fun getUserVisits(@PathVariable("userId") userId: Long): List<DetailedVisit> {
        return visitService.getUserVisits(userId)
    }
}