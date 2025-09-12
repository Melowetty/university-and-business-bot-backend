package ru.sigma.hse.business.bot.domain.model

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalTime

data class Activity(
    @Schema(description = "ID активности", example = "1")
    override val id: Long,

    @Schema(description = "Название активности", example = "Собрание")
    override val name: String,

    @Schema(description = "Описание активности", example = "Важное собрание для первокурсников")
    override val description: String,

    @Schema(description = "Тип активности", example = "LECTURE")
    val activityType: ActivityType,

    @Schema(description = "Место проведения активности", example = "Актовый зал")
    var location: String,

    @Schema(description = "Время начала", example = "10:10")
    var startTime: LocalTime,

    @Schema(description = "Время окончания", example = "11:50")
    var endTime: LocalTime,

    @Schema(description = "Баллы за выполнения ивента", example = "20")
    val points: Int
) : Visitable(id, name, description)
