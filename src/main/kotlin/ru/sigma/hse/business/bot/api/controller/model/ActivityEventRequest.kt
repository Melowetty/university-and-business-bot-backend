package ru.sigma.hse.business.bot.api.controller.model

import io.swagger.v3.oas.annotations.media.Schema
import ru.sigma.hse.business.bot.domain.model.EventStatus
import java.time.Duration

class ActivityEventRequest(
    @Schema(description = "ID активности", example = "1")
    val id: Long,
    @Schema(description = "Название ивента", example = "Голосование за проект")
    val name: String,
    @Schema(description = "Описание ивента", example = "Проголосуйте за проект, который больше всего Вам понравился")
    val description: String,
    @Schema(description = "Длительность ивента", example = "P10m")
    val duration: Duration?,
    @Schema(description = "Статус ивента", example = "ENDED")
    var status: EventStatus,
    @Schema(description = "Ответы ивента", example = "['раз','двас','три3']")
    val answers: List<String>
)