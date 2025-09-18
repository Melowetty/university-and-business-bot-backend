package ru.sigma.hse.business.bot.api.controller.model

import io.swagger.v3.oas.annotations.media.Schema
import ru.sigma.hse.business.bot.domain.model.UserRole

class GetUserInfoRequest(
    @Schema(description = "Id пользователя", example = "1")
    val userId: Long,
    @Schema(description = "Имя пользователя", example = "Павел")
    val name: String,
    @Schema(description = "Роль пользователя", example = "ADMIN")
    val role: UserRole,
    @Schema(description = "Курс пользователя", example = "1")
    val course: Int,
    @Schema(description = "Программа пользователя", example = "РИС")
    val program: String,
    @Schema(description = "Email пользователя", example = "example@ex.pl")
    val email: String?
)