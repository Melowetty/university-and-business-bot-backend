package ru.sigma.hse.business.bot.domain.model

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.Column

data class Event (
    @Schema(description = "ID ивента", example = "1")
    val id: Long,
    @Schema(description = "Статус ивента", example = "ENDED")
    var status: EventStatus,
    @Schema(description = "Ответы ивента", example = "['раз','двас','три3']")
    val answers: List<String>
)