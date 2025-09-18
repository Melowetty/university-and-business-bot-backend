package ru.sigma.hse.business.bot.api.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.sigma.hse.business.bot.api.controller.model.task.CreateTaskRequest
import ru.sigma.hse.business.bot.api.controller.model.task.TaskStatus
import ru.sigma.hse.business.bot.api.controller.model.task.UpdateTaskStatusRequest
import ru.sigma.hse.business.bot.domain.model.CompletedUserTask
import ru.sigma.hse.business.bot.domain.model.Task
import ru.sigma.hse.business.bot.exception.base.BadArgumentException
import ru.sigma.hse.business.bot.service.TaskService
import ru.sigma.hse.business.bot.service.TelegramUserService

@RestController
@RequestMapping("/tasks")
@Tag(name = "Admin: Задания", description = "Работа с заданиями")
class TaskController(
    private val taskService: TaskService,
    private val telegramUserService: TelegramUserService,
) {
    @GetMapping(
        produces = ["application/json"]
    )
    @Operation(
        summary = "Получить все задания",
        description = "Получить все задания"
    )
    fun getAllTasks(): List<Task> {
        return taskService.getAllTasks()
    }

    @PostMapping(
        produces = ["application/json"]
    )
    @Operation(
        summary = "Создать задание",
        description = "Создать задание"
    )
    @ApiResponse(responseCode = "200", description = "Создано новое задание")
    fun createTask(
        @Parameter(description = "ID задания")
        @RequestBody request: CreateTaskRequest
    ): Task {
        return taskService.createTask(request)
    }

    @GetMapping(
        "/{taskId}",
        produces = ["application/json"]
    )
    @Operation(
        summary = "Получить задание",
        description = "Информация о задании"
    )
    @ApiResponse(responseCode = "200", description = "Информация о задании")
    fun getTask(
        @Parameter(description = "ID задания")
        @PathVariable("taskId") taskId: Long
    ): Task {
        return taskService.getTask(taskId)
    }

    @PostMapping(
        "/all/status",
    )
    @Operation(
        summary = "Изменить статус всех заданий (можно только завершить)",
        description = "Изменить статус всех заданий (можно только завершить)"
    )
    fun changeAllTasksStatus(
        @RequestBody status: UpdateTaskStatusRequest
    ) {
        return when(status.status) {
            TaskStatus.RUN -> throw BadArgumentException("ILLEGAL_UPDATE_STATUS_REQUEST", "Cannot change status of all tasks to RUN")
            TaskStatus.END -> taskService.endAllTasks()
        }
    }

    @PostMapping(
        "/{taskId}/status",
        produces = ["application/json"]
    )
    @Operation(
        summary = "Изменить статус задания",
        description = "Изменить статус задания"
    )
    @ApiResponse(responseCode = "200", description = "Измененное задание")
    fun changeTaskStatus(
        @Parameter(description = "ID задания")
        @PathVariable("taskId") taskId: Long,
        @RequestBody status: UpdateTaskStatusRequest
    ): Task {
        return when(status.status) {
            TaskStatus.RUN -> taskService.startTask(taskId)
            TaskStatus.END -> taskService.endTask(taskId)
        }
    }

    @PostMapping(
        "/{taskId}/submit/{telegramId}",
        produces = ["application/json"]
    )
    @Operation(
        summary = "Выполнить задание",
        description = "Сделать отметку выполнения задания"
    )
    @ApiResponse(responseCode = "200", description = "Пользователь выполнил новое задание")
    @ApiResponse(responseCode = "409", description = "Пользователь уже выполнил это задание")
    fun completeTask(
        @Parameter(description = "Telegram ID пользователя")
        @PathVariable("telegramId") telegramId: Long,
        @Parameter(description = "ID задания")
        @PathVariable("taskId") taskId: Long
    ): CompletedUserTask {
        val userId = telegramUserService.getUser(telegramId).id
        return taskService.completeTask(userId, taskId)
    }

}