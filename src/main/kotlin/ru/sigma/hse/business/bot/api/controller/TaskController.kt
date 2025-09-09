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
import ru.sigma.hse.business.bot.api.controller.model.CreateTaskRequest
import ru.sigma.hse.business.bot.domain.model.Task
import ru.sigma.hse.business.bot.service.TaskService

@RestController
@RequestMapping("/users/{telegramId}/tasks")
@Tag(name = "Задания", description = "Работа с заданиями")
class TaskController(
    private val taskService: TaskService,
) {
    @PostMapping(
        produces = ["application/json"]
    )
    @Operation(
        summary = "Создать задание",
        description = "Создать задание"
    )
    @ApiResponse(responseCode = "200", description = "Создано новое задание")
    fun createTask(
        @Parameter(description = "Уникальный код задания")
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
        @Parameter(description = "Уникальный код задания")
        @PathVariable("taskId") taskId: Long
    ): Task {
        return taskService.getTask(taskId)
    }

    @PostMapping(
        "/{taskId}",
        produces = ["application/json"]
    )
    @Operation(
        summary = "Начать задание",
        description = "Начать задание"
    )
    @ApiResponse(responseCode = "200", description = "Стартовать задание")
    fun startTask(
        @Parameter(description = "Уникальный код задания")
        @PathVariable("taskId") taskId: Long
    ): Task {
        return taskService.startTask(taskId)
    }

}