package ru.sigma.hse.business.bot.api.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.sigma.hse.business.bot.api.controller.model.CreatePreregistrationUserRequest
import ru.sigma.hse.business.bot.api.controller.model.CreateUserRequest
import ru.sigma.hse.business.bot.api.controller.model.GetUserInfoRequest
import ru.sigma.hse.business.bot.domain.model.PreregistrationUser
import ru.sigma.hse.business.bot.service.PreregistrationUserService
import ru.sigma.hse.business.bot.service.TelegramUserService

@RestController
@RequestMapping("/preregistration/users")
@Tag(name = "Пользователи предварительной регистрации", description = "Управление пользователями предварительной регистрации")
class PreregistrationUserController(
    private val preregistrationUserService: PreregistrationUserService
) {
    @PostMapping(
        produces = ["application/json"]
    )
    @Operation(
        summary = "Добавить пользователя",
        description = "Добавить нового пользователя с указанием м телеграмм ID"
    )
    @ApiResponse(responseCode = "200", description = "Новый пользователь успешно создан")
    fun createPreregistrationUser(
        @Parameter(description = "Объект с id пользователя")
        @RequestBody newUser: CreatePreregistrationUserRequest
    ): PreregistrationUser {
        return preregistrationUserService.createPreregistrationUser(newUser)
    }

    @GetMapping(
        "/{telegramId}",
        produces = ["application/json"]
    )
    @Operation(
        summary = "Получить данные о пользователе",
        description = "Выдает данные в случае существования пользователя в базе данных"
    )
    @ApiResponse(responseCode = "200", description = "Информация о пользователе успешно выведена")
    fun getPreregistrationUser(
        @Parameter(description = "Телеграмм Id пользователя")
        @PathVariable telegramId: Long
    ): PreregistrationUser {
        val user = preregistrationUserService.getPreregistrationUser(telegramId)
        return user
    }
}