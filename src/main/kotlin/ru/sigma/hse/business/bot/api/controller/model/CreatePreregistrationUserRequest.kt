package ru.sigma.hse.business.bot.api.controller.model

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Positive

class CreatePreregistrationUserRequest(
    @field:Positive(message = "Wrong preregistration user telegram id format (must be positive)")
    @Schema(description = "Telegram iD пользователя", example = "123456789")
    val tgId: Long
)