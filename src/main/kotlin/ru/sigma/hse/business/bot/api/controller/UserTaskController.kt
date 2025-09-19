package ru.sigma.hse.business.bot.api.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.sigma.hse.business.bot.api.controller.model.task.UserTask
import ru.sigma.hse.business.bot.service.TelegramUserService
import ru.sigma.hse.business.bot.service.UserTaskService

@RestController
@RequestMapping("/users/{telegramId}/tasks")
@Tag(name = "Выполнение заданий пользователями", description = "Работа с отметкой выполнения заданий пользователями")
class UserTaskController(
    private val userTaskService: UserTaskService,
    private val telegramUserService: TelegramUserService,
) {
    @GetMapping(
        produces = ["application/json"]
    )
    @Operation(
        summary = "Получить задания пользователя",
        description = "Все задания пользователя"
    )
    @ApiResponse(responseCode = "200", description = "Список заданий пользователя")
    fun getTasks(
        @Parameter(description = "Telegram ID пользователя")
        @PathVariable("telegramId") telegramId: Long
    ): List<UserTask> {
        val userId = telegramUserService.getUser(telegramId).id
        return userTaskService.getAvailableUserTasks(userId)
    }

    @GetMapping(
        "/{taskId}",
        produces = ["application/json"]
    )
    @Operation(
        summary = "Получить задание пользователя",
        description = "Задание пользователя"
    )
    @ApiResponse(responseCode = "200", description = "Задание пользователя")
    @ApiResponse(responseCode = "400", description = "Задание пользователя недоступно к выполнению")
    fun getTask(
        @Parameter(description = "Telegram ID пользователя")
        @PathVariable("telegramId") telegramId: Long,
        @Parameter(description = "ID задания")
        @PathVariable("taskId") taskId: Long
    ) : UserTask {
        val userId = telegramUserService.getUser(telegramId).id
        return userTaskService.getTask(userId, taskId)
    }
}