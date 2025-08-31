package ru.sigma.hse.business.bot.api.controller.model

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalTime


data class CreateActivityRequest(
    @Schema(description = "Название активности", example = "Собрание")
    val name: String,
    @Schema(description = "Описание активности", example = "Важное собрание для первокурсников")
    val description: String,
    @Schema(description = "Место проведение активности", example = "Актовый зал")
    val location: String,
    @Schema(description = "Время начала", example = "2025-09-01 10:00:00")
    val startTime: LocalTime,
    @Schema(description = "Время окончания", example = "2025-09-01 10:30:00")
    val endTime: LocalTime
)