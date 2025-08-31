package ru.sigma.hse.business.bot.domain.model

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalTime

data class Activity(
    @Schema(description = "Id астивности", example = "1")
    override val id: Long,
    @Schema(description = "Название активности", example = "Собрание")
    override val name: String,
    @Schema(description = "Описание активности", example = "Важное собрание для первокурсников")
    override val description: String,
    @Schema(description = "Место проведеняи активности", example = "Актовый зал")
    var location: String,
    @Schema(description = "Время начала", example = "2025-09-01 10:10:00")
    var startTime: LocalTime,
    @Schema(description = "Время окончания", example = "2025-09-01 11:50:00")
    var endTime: LocalTime,
) : Visitable(id, name, description)
