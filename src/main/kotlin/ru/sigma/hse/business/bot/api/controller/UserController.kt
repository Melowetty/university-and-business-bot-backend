package ru.sigma.hse.business.bot.api.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.sigma.hse.business.bot.api.controller.model.CreateUserRequest
import ru.sigma.hse.business.bot.api.controller.model.GetUserInfoRequest
import ru.sigma.hse.business.bot.service.TelegramUserService
import ru.sigma.hse.business.bot.service.UserService

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService,
    private val telegramUserService: TelegramUserService
) {
    @PostMapping()
    @Operation(
        summary = "Добавить пользователя",
        description = "Добавить нового пользователя с указание м телеграм id"
    )
    @ApiResponse(responseCode = "200", description = "Новый пользователь успешно создан")
    fun createUser(
        @Parameter(description = "Объект с полями пользователя")
        @RequestBody newUser: CreateUserRequest
    ): String {
        return userService.createUser(newUser)
    }

    @GetMapping("/{telegramId}")
    @Operation(
        summary = "Получить данные о пользователе",
        description = "Отдает объект с полной информацией о пользователе и количесвте посещенных им компаний и активностей, а также его игровой счёт"
    )
    @ApiResponse(responseCode = "200", description = "Информация о пользователе успешно выведена")
    fun getUser(
        @Parameter(description = "Телеграм id пользователя")
        @PathVariable telegramId: Long
    ): GetUserInfoRequest {
        val userId = telegramUserService.getUser(telegramId).id
        return userService.getUserInfo(userId)
    }
}