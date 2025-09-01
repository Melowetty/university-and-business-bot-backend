package ru.sigma.hse.business.bot.api.controller.model

import io.swagger.v3.oas.annotations.media.Schema

class GetUserInfoRequest(
    @Schema(description = "Id пользователя", example = "1")
    val userId: Long,
    @Schema(description = "Имя пользователя", example = "Павел")
    val name: String,
    @Schema(description = "Количество посещенных пользователем компаний", example = "4")
    val companyCount: Int,
    @Schema(description = "Количество посещенных пользователем активностей", example = "1")
    val activityCount: Int,
    @Schema(description = "Игровой счёт пользователя", example = "15")
    val scoreCount: Int
)