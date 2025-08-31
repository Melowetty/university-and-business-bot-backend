package ru.sigma.hse.business.bot.api.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.sigma.hse.business.bot.domain.model.DetailedVisit
import ru.sigma.hse.business.bot.service.TelegramUserService
import ru.sigma.hse.business.bot.service.VisitService

@RestController
@RequestMapping("/users/{telegramId}/visits")
class VisitController(
    private val visitService: VisitService,
    private val telegramUserService: TelegramUserService
) {
    @PostMapping("/{code}")
    @Operation(
        summary = "Посетить активность",
        description = "Вводится тг id  пользоватлея и код ивента"
    )
    @ApiResponse(responseCode = "200", description = "Пользователь посетил новый для себя ивент")
    @ApiResponse(responseCode = "500", description = "Пользователоь уже посещал этот ивент")
    fun visitFlow(
        @Parameter(description = "Telegram id пользователя")
        @PathVariable("telegramId") telegramId: Long,
        @Parameter(description = "Уникальный код ивента")
        @PathVariable("code") code: String
    ): DetailedVisit {
        val userId = telegramUserService.getUser(telegramId).id
        return visitService.visit(userId, code)
    }

    @GetMapping
    @Operation(
        summary = "Получить посещения пользователя",
        description = "Все посещенные пользователем мероприятния и активности"
    )
    @ApiResponse(responseCode = "200", description = "Информация о посещениях пользователя вывелась")
    fun getUserVisits(
        @Parameter(description = "Telegram id пользователя")
        @PathVariable("telegramId") telegramId: Long
    ): List<DetailedVisit> {
        val userId = telegramUserService.getUser(telegramId).id
        return visitService.getUserVisits(userId)
    }
}