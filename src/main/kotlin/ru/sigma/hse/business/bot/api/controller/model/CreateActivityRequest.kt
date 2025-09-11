package ru.sigma.hse.business.bot.api.controller.model

import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.FutureOrPresent
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import java.time.LocalTime
import ru.sigma.hse.business.bot.utils.Constants


data class CreateActivityRequest(
    @field:NotBlank(message = "Wrong activity name format")
    @Schema(description = "Название активности", example = "Собрание")
    val name: String,
    @field:NotBlank(message = "Wrong activity description format")
    @Schema(description = "Описание активности", example = "Важное собрание для первокурсников")
    val description: String,
    @field:NotBlank(message = "Wrong activity location format")
    @Schema(description = "Место проведение активности", example = "Актовый зал")
    val location: String,
    @field:FutureOrPresent(message = "Wrong activity start time format (must be present or future)")
    @Schema(description = "Время начала (часовой пояс Перми)", example = "10:00")
    @JsonFormat(pattern = Constants.TIME_FORMAT)
    val startTime: LocalTime,
    @field:FutureOrPresent(message = "Wrong activity end time format (must be present or future)")
    @Schema(description = "Время окончания (часовой пояс Перми)", example = "10:30")
    @JsonFormat(pattern = Constants.TIME_FORMAT)
    val endTime: LocalTime,
    @field:NotBlank(message = "Wrong activity keyWord format")
    @Schema(description = "Ключевое слово ивента", example = "Веном")
    val keyWord: String,
    @field:Positive(message = "Wrong activity points format")
    @Schema(description = "Баллы ха выполнение ивента", example = "15")
    val points: Int
)