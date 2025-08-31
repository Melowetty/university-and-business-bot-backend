package ru.sigma.hse.business.bot.api.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.sigma.hse.business.bot.api.controller.model.VisitResult
import ru.sigma.hse.business.bot.domain.model.DetailedVisit
import ru.sigma.hse.business.bot.service.TelegramUserService
import ru.sigma.hse.business.bot.service.VisitService

@RestController
@RequestMapping("/users/{telegramId}/visits")
@Tag(name = "Посещения", description = "Работа с визитами")
class VisitController(
    private val visitService: VisitService,
    private val telegramUserService: TelegramUserService
) {
    @PostMapping(
        "/{code}",
        produces = ["application/json"]
    )
    @Operation(
        summary = "Посетить активность",
        description = "Сделать отметку посещения активности"
    )
    @ApiResponse(responseCode = "200", description = "Пользователь посетил новый для себя ивент")
    @ApiResponse(responseCode = "409", description = "Пользователь уже посещал этот ивент")
    fun visitFlow(
        @Parameter(description = "Telegram ID пользователя")
        @PathVariable("telegramId") telegramId: Long,
        @Parameter(description = "Уникальный код ивента")
        @PathVariable("code") code: String
    ): VisitResult {
        val userId = telegramUserService.getUser(telegramId).id
        return visitService.visit(userId, code)
    }

    @GetMapping(
        produces = ["application/json"]
    )
    @Operation(
        summary = "Получить посещения пользователя",
        description = "Все посещенные пользователем мероприятния и активности"
    )
    @ApiResponse(responseCode = "200", description = "Список посещений")
    fun getUserVisits(
        @Parameter(description = "Telegram ID пользователя")
        @PathVariable("telegramId") telegramId: Long
    ): List<DetailedVisit> {
        val userId = telegramUserService.getUser(telegramId).id
        return visitService.getUserVisits(userId)
    }
}