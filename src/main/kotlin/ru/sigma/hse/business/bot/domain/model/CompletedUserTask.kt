package ru.sigma.hse.business.bot.domain.model

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalTime

data class CompletedUserTask (
    @Schema(description = "ID выполнения", example = "1")
    val id: Long,
    @Schema(description = "ID пользователя", example = "2")
    val userId: Long,
    @Schema(description = "ID задания", example = "3")
    val taskId: Long,
    @Schema(description = "Время момента выполнения задания", example = "12:22")
    val timeTaken: LocalTime,
)