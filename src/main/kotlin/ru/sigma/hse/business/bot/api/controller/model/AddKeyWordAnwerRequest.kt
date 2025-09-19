package ru.sigma.hse.business.bot.api.controller.model

import io.swagger.v3.oas.annotations.media.Schema
import ru.sigma.hse.business.bot.domain.model.EventStatus
import java.time.Duration

class AddKeyWordAnwerRequest(
    @Schema(description = "Телеграмм ID пользователя", example = "123456789")
    val tgId: Long,
    @Schema(description = "Введённое кодовое слово", example = "Какащка")
    val keyWord: String,
)