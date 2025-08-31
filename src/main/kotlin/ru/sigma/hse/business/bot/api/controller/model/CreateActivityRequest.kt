package ru.sigma.hse.business.bot.api.controller.model

import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalTime
import ru.sigma.hse.business.bot.utils.Constants


data class CreateActivityRequest(
    @Schema(description = "Название активности", example = "Собрание")
    val name: String,
    @Schema(description = "Описание активности", example = "Важное собрание для первокурсников")
    val description: String,
    @Schema(description = "Место проведение активности", example = "Актовый зал")
    val location: String,
    @Schema(description = "Время начала (часовой пояс Перми)", example = "10:00")
    @JsonFormat(pattern = Constants.TIME_FORMAT)
    val startTime: LocalTime,
    @Schema(description = "Время окончания (часовой пояс Перми)", example = "10:30")
    @JsonFormat(pattern = Constants.TIME_FORMAT)
    val endTime: LocalTime
)