package ru.sigma.hse.business.bot.api.controller.model

import io.swagger.v3.oas.annotations.media.Schema
import ru.sigma.hse.business.bot.domain.model.VisitTarget
import ru.sigma.hse.business.bot.domain.model.Visitable

data class VisitResult(
    @Schema(description = "Информция о посещенном объекте")
    val target: Visitable,

    @Schema(description = "Тип посещенного объекта", example = "COMPANY")
    val targetType: VisitTarget,

    @Schema(description = "Флаг, указывающий, что пользователь только что завершил конференцию")
    val isCompleteConference: Boolean
)
