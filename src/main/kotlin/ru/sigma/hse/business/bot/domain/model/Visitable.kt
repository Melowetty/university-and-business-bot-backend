package ru.sigma.hse.business.bot.domain.model

import io.swagger.v3.oas.annotations.media.Schema

sealed class Visitable(
    @Schema(description = "ID объекта", example = "1")
    open val id: Long,
    @Schema(description = "Название объекта", example = "Ростикс")
    open val name: String,
    @Schema(description = "Описание объекта", example = "Компания со вкусными крылышками")
    open val description: String,
)