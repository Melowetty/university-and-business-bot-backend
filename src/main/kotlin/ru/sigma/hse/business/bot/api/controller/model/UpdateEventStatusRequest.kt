package ru.sigma.hse.business.bot.api.controller.model

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import ru.sigma.hse.business.bot.domain.model.EventStatus

class UpdateEventStatusRequest(
    @field:NotBlank(message = "Wrong event status format")
    @Schema(description = "Статус голосования", example = "PREPARED или CONTINUED или ENDED")
    val status: EventStatus
)