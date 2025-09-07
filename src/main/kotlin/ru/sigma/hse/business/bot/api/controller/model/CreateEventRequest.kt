package ru.sigma.hse.business.bot.api.controller.model

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty

class CreateEventRequest(
    @field:NotEmpty(message = "Wrong event answer format")
    @Schema(description = "Варианты ответов к голосованию", example = "{'Один', 'Два', 'Три'}")
    val answers: List<@NotBlank(message = "Wrong answer element format")String>,
    @field:NotBlank(message = "Wrong event right answer format") //?????
    @Schema(description = "Правильный ответ", example = "Один")
    val rightAnswer: String
)