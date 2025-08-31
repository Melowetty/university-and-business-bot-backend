package ru.sigma.hse.business.bot.api.controller.model

import io.swagger.v3.oas.annotations.media.Schema

data class CreateUserRequest(
    @Schema(description = "Telegram id пользователя", example = "123456789")
    val tgId: Long,
    @Schema(description = "Имя пользователя", example = "Павел")
    val fullName: String,
    @Schema(description = "Курс пользователя", example = "1")
    val course: Int,
    @Schema(description = "Направление пользователя", example = "РИС")
    val program: String,
    @Schema(description = "Почта пользователя", example = "example@ex.pl")
    val email: String?
)