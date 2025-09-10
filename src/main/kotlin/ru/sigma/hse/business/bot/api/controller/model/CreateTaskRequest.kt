package ru.sigma.hse.business.bot.api.controller.model

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Positive
import ru.sigma.hse.business.bot.domain.model.TaskType

class CreateTaskRequest(
    @field:NotBlank(message = "Wrong task name format")
    @Schema(description = "Название задания", example = "Видео")
    val name: String,
    @field:NotBlank(message = "Wrong task name format")
    @Schema(description = "Тип задания", example = "TEMP")
    val type: TaskType,
    @field:NotBlank(message = "Wrong task description format")
    @Schema(description = "Описание задания", example = "Подпрыгни три раза на видео")
    val description: String,
    @field:Positive(message = "Wrong task points format (must be positive)")
    @Schema(description = "Баллы за выполнение", example = "15")
    val points: Int,
    @field:Positive(message = "Wrong task duration format (must be positive)")
    @Schema(description = "Время длительности задания в минутах", example = "3")
    val duration: Long
)