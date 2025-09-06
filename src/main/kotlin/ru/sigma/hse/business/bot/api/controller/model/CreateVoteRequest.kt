package ru.sigma.hse.business.bot.api.controller.model

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Positive

class CreateVoteRequest(
    @field:Positive(message = "Wrong user telegram id format (must be positive)")
    @Schema(description = "Telegram iD пользователя", example = "123456789")
    val userTgId: Long,
    @field:NotBlank(message = "Wrong vote answer format")
    @Schema(description = "Ответ к голосованию", example = "Один")
    val answer: String
)