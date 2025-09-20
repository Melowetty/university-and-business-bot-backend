package ru.sigma.hse.business.bot.domain.model

import io.swagger.v3.oas.annotations.media.Schema

data class DetailedVisit(
    @Schema(description = "Посещенный объект")
    val target: Visitable,
    @Schema(description = "Тип посещенного объекта", example = "COMPANY")
    val targetType: VisitTarget,
)
