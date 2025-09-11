package ru.sigma.hse.business.bot.api.controller.model

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.PositiveOrZero
import java.time.Duration

class CreateEventRequest(
    @field:NotBlank(message = "Wrong event name format")
    @Schema(description = "Название ивента", example = "Голосование за лучший проект")
    val name: String,

    @field:NotBlank(message = "Wrong event description format")
    @Schema(description = "Описание ивента", example = "Проголосуйте за лучший проект")
    val description: String,

    @Schema(description = "Длительность ивента по стандарту ISO 8601", example = "P5m")
    val duration: Duration?,

    @field:NotEmpty(message = "Wrong event answer format")
    @Schema(description = "Варианты ответов к голосованию", example = "{'Один', 'Два', 'Три'}")
    val answers: List<@NotBlank(message = "Wrong answer element format")String>,

    @Schema(description = "Правильный ответ", example = "Один")
    val rightAnswer: String?,

    @field:PositiveOrZero(message = "Wrong event reward format")
    @Schema(description = "Награда за прохождение ивента", example = "100")
    val reward: Int
)