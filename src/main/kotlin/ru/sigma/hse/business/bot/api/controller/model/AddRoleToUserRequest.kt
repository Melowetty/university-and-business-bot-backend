package ru.sigma.hse.business.bot.api.controller.model

import io.swagger.v3.oas.annotations.media.Schema
import ru.sigma.hse.business.bot.domain.model.EventStatus
import java.time.Duration

class AddRoleToUserRequest(
    @Schema(description = "TGID пользователя", example = "1")
    val tgId: Long,
    @Schema(description = "Код роли", example = "FOSfWRSGEq")
    val code: String
)