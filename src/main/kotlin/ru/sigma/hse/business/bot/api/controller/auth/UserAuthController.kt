package ru.sigma.hse.business.bot.api.controller.auth

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.sigma.hse.business.bot.api.controller.model.AddRoleToUserRequest
import ru.sigma.hse.business.bot.api.controller.model.CreateAuthCodeRequest
import ru.sigma.hse.business.bot.service.AuthCodeService
import ru.sigma.hse.business.bot.service.TelegramUserService

@RestController
@RequestMapping("/auth/code")
@Tag(name = "Роли", description = "Управление ролями пользователей")
class UserAuthController(
    private val authCodeService: AuthCodeService,
    private val telegramUserService: TelegramUserService
) {
    @PostMapping(
        "/generate",
        produces = ["application/json"]
    )
    @Operation(
        summary = "Получить код новыйх ролей",
        description = "Добавить указанное количество ролей и получить их код"
    )
    @ApiResponse(responseCode = "200", description = "Новый роли успешно созданы")
    fun createRoles(
        @Parameter(description = "Объект с количеством и типом ролей")
        @RequestBody request: CreateAuthCodeRequest
    ): List<String> {
        return authCodeService.generateAuthCode(request)
    }

    @PostMapping(
        "/activate",
        produces = ["application/json"]
    )
    @Operation(
        summary = "Добавить роль пользователю",
        description = "Добавить пользователю новую роль"
    )
    @ApiResponse(responseCode = "200", description = "Пользователь успешно получил новую роль")
    fun addRolesToUser(
        @Parameter(description = "Объект с tg id пользователя и ролью")
        @RequestBody addUserRole: AddRoleToUserRequest
    ) {
        val user = telegramUserService.getUser(addUserRole.tgId)
        authCodeService.addRole(user.id,addUserRole.code)
    }
}