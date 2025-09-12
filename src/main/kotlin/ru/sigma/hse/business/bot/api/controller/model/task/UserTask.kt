package ru.sigma.hse.business.bot.api.controller.model.task

import io.swagger.v3.oas.annotations.media.Schema

data class UserTask(
    @Schema(description = "ID задания", example = "1")
    val id: Long,
    @Schema(description = "Название задания", example = "Видео")
    val name: String,
    @Schema(description = "Описание задания", example = "Подпрыгни три раза на видео")
    val description: String,
    @Schema(description = "Задание еще можно выполнить", example = "true")
    val isAvailable: Boolean,
    @Schema(description = "Статус задания для пользователя", example = "DONE")
    val status: UserTaskStatus,
    @Schema(description = "Тип задания", example = "BE_REAL")
    val taskType: UserTaskType,
)