package ru.sigma.hse.business.bot.domain.model

import io.swagger.v3.oas.annotations.media.Schema
import java.time.Duration

data class Task (
    @Schema(description = "ID задания", example = "1")
    val id: Long,
    @Schema(description = "Тип задания", example = "TEMP")
    val type: TaskType,
    @Schema(description = "Время длительности задания", example = "00:05:00")
    var duration: Duration?,
    @Schema(description = "Название задания", example = "Видео")
    val name: String,
    @Schema(description = "Состояние задания", example = "FINISHED")
    var status: TaskStatus,
    @Schema(description = "Описание задания", example = "Подпрыгни три раза на видео")
    val description: String,
    @Schema(description = "Баллы за выполнение", example = "15")
    val points: Int
)