package ru.sigma.hse.business.bot.api.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.sigma.hse.business.bot.domain.model.CompletedUserTask
import ru.sigma.hse.business.bot.service.TelegramUserService
import ru.sigma.hse.business.bot.service.TaskService

@RestController
@RequestMapping("/users/{telegramId}/tasks")
@Tag(name = "Выполнение заданий пользователями", description = "Работа с отметкой выполнения заданий пользователями")
class UserTaskController(
    private val taskService: TaskService,
    private val telegramUserService: TelegramUserService
) {
    @PostMapping(
        "/{taskId}",
        produces = ["application/json"]
    )
    @Operation(
        summary = "Выполнить задание",
        description = "Сделать отметку выполнения задания"
    )
    @ApiResponse(responseCode = "200", description = "Пользователь выполнил новое задание")
//    @ApiResponse(responseCode = "409", description = "Пользователь уже выполнил это задание")
    fun completeTask(
        @Parameter(description = "Telegram ID пользователя")
        @PathVariable("telegramId") telegramId: Long,
        @Parameter(description = "ID задания")
        @PathVariable("taskId") taskId: Long
    ): CompletedUserTask {
        val userId = telegramUserService.getUser(telegramId).id
        return taskService.completeTask(userId, taskId)
    }

    @GetMapping(
        produces = ["application/json"]
    )
    @Operation(
        summary = "Получить выполненные задания пользователя",
        description = "Все выполненные пользователем задания"
    )
    @ApiResponse(responseCode = "200", description = "Список посещённых заданий")
    fun getUserVisits(
        @Parameter(description = "Telegram ID пользователя")
        @PathVariable("telegramId") telegramId: Long
    ): List<CompletedUserTask> {
        val userId = telegramUserService.getUser(telegramId).id
        return taskService.getCompletedTasksByUserId(userId)
    }
}