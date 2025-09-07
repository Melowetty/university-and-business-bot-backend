package ru.sigma.hse.business.bot.service

import org.springframework.stereotype.Service
import ru.sigma.hse.business.bot.api.controller.model.CreateTaskRequest
import ru.sigma.hse.business.bot.domain.model.CompletedUserTask
import ru.sigma.hse.business.bot.domain.model.Task
import ru.sigma.hse.business.bot.persistence.CompletedUserTaskStorage
import ru.sigma.hse.business.bot.persistence.TaskStorage


@Service
class TaskService(
    private val taskStorage: TaskStorage,
    private val completedUserTaskStorage: CompletedUserTaskStorage,
    ) {
    fun createTask(request: CreateTaskRequest): Task {
        return taskStorage.createTask(
            name = request.name,
            description = request.description,
            points = request.points
        )
    }

    fun getTask(taskId: Long): Task {
        return taskStorage.getTask(taskId)
            ?: throw IllegalArgumentException("Task not found")
    }

    fun uptadeTaskAvailable(taskId: Long, isAvaible: Boolean): Task {
        return taskStorage.updateTaskStatus(taskId, isAvaible)
    }

    fun completeTask(userId: Long, taskId: Long): CompletedUserTask {
        return completedUserTaskStorage.createCompletedUserTask(userId, taskId)
    }

    fun getCompletedTasksByUserId(userId: Long): List<CompletedUserTask> {
        return completedUserTaskStorage.getCompletedUserTasksByUserId(userId)
    }
}