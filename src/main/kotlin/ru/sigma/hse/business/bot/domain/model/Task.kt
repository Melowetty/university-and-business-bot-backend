package ru.sigma.hse.business.bot.domain.model

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.Column

data class Task (
    @Schema(description = "ID задания", example = "1")
    val id: Long,
    @Schema(description = "Название задания", example = "Видео")
    val name: String,
    @Schema(description = "Доступность задания", example = "true")
    var isAvailable: Boolean,
    @Schema(description = "Описание задания", example = "Подпрыгни три раза на видео")
    val description: String,
    @Schema(description = "Баллы за выполнение", example = "15")
    val points: Int
)