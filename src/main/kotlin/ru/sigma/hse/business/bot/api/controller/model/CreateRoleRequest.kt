package ru.sigma.hse.business.bot.api.controller.model

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import ru.sigma.hse.business.bot.domain.model.UserRole

class CreateRoleRequest(
    @field:Positive(message = "Wrong role count format (must be positive)")
    @Schema(description = "Количество генерируемых ролей", example = "3")
    val count: Int,
    @field:NotBlank(message = "Wrong role type format")
    @Schema(description = "Тип роли", example = "ADMIN")
    val type: UserRole
)