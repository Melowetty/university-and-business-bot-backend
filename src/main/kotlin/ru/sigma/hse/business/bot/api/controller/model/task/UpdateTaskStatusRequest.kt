package ru.sigma.hse.business.bot.api.controller.model.task

data class UpdateTaskStatusRequest(
    val status: TaskStatus,
)

enum class TaskStatus {
    RUN,
    END
}
