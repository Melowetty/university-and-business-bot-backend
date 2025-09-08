package ru.sigma.hse.business.bot.domain.model

import io.swagger.v3.oas.annotations.media.Schema

data class Vote (
    @Schema(description = "ID голоса", example = "1")
    val id: Long,
    @Schema(description = "ID пользователя", example = "2")
    val userId: Long,
    @Schema(description = "Ответ пользователя", example = "Страуструп")
    val answer: String,
    @Schema(description = "ID ивента", example = "4")
    val eventId: Long
)