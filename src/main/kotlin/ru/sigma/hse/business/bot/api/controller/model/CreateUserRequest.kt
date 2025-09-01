package ru.sigma.hse.business.bot.api.controller.model

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive

data class CreateUserRequest(
    @field:Positive(message = "Wrong user telegram id format (must be positive)")
    @field:Digits(integer = 9, fraction = 0, message = "Wrong user telegram id format (must include only 9 numbers)")
    @Schema(description = "Telegram iD пользователя", example = "123456789")
    val tgId: Long,
    @field:NotBlank(message = "Wrong user name format")
    @Schema(description = "Имя пользователя", example = "Павел")
    val fullName: String,
    @field:Min(1,message = "Wrong user course format (must be between 1 and 5)")
    @field:Max(5,message = "Wrong user course format (must be between 1 and 5)")
    @Schema(description = "Курс пользователя", example = "1")
    val course: Int,
    @field:NotBlank(message = "Wrong user program format")
    @Schema(description = "Направление пользователя", example = "РИС")
    val program: String,
    @field:Email(message = "Wrong user email format")
    @Schema(description = "Почта пользователя", example = "example@ex.pl")
    val email: String?
)