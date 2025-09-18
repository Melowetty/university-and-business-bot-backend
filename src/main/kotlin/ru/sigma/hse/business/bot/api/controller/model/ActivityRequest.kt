package ru.sigma.hse.business.bot.api.controller.model

import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.v3.oas.annotations.media.Schema
import ru.sigma.hse.business.bot.domain.model.ActivityType
import ru.sigma.hse.business.bot.domain.model.EventStatus
import java.time.Duration
import java.time.LocalTime

class ActivityRequest(
    @Schema(description = "ID активности", example = "1")
    val id: Long,

    @Schema(description = "Название активности", example = "Собрание")
    val name: String,

    @Schema(description = "Описание активности", example = "Важное собрание для первокурсников")
    val description: String,

    @Schema(description = "Тип активности", example = "LECTURE")
    val activityType: ActivityType,

    @Schema(description = "Место проведения активности", example = "Актовый зал")
    val location: String,

    @Schema(description = "Время начала", example = "10:10")
    @JsonFormat(pattern = "HH:mm")
    val startTime: LocalTime,

    @Schema(description = "Время окончания", example = "11:50")
    @JsonFormat(pattern = "HH:mm")
    val endTime: LocalTime,

    @Schema(description = "Имеет ли событие", example = "true")
    val hasEvent: Boolean
)