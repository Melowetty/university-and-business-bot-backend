package ru.sigma.hse.business.bot.domain.model

import io.swagger.v3.oas.annotations.media.Schema

data class DetailedVisit(
    @Schema(description = "Это подкласс со всей инфой", example = "низнаю как будет выглядеть")
    val target: Visitable,
    @Schema(description = "Тип посещенного", example = "COMPANY")
    val type: VisitTarget,
)
