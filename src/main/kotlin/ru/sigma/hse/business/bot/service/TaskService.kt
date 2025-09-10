package ru.sigma.hse.business.bot.service

import org.springframework.stereotype.Service
import ru.sigma.hse.business.bot.api.controller.model.CreateTaskRequest
import ru.sigma.hse.business.bot.domain.model.CompletedUserTask
import ru.sigma.hse.business.bot.domain.model.Task
import ru.sigma.hse.business.bot.domain.model.TaskStatus
import ru.sigma.hse.business.bot.exception.task.TaskByIdNotFoundException
import ru.sigma.hse.business.bot.persistence.CompletedUserTaskStorage
import ru.sigma.hse.business.bot.persistence.TaskStorage
import java.time.Duration


@Service
class TaskService(
    private val taskStorage: TaskStorage,
    private val completedUserTaskStorage: CompletedUserTaskStorage,
    ) {
    fun createTask(request: CreateTaskRequest): Task {
        return taskStorage.createTask(
            name = request.name,
            type = request.type,
            description = request.description,
            points = request.points,
            duration = Duration.ofMinutes(request.duration)
        )
    }

    fun getTask(taskId: Long): Task {
        return taskStorage.getTask(taskId)
            ?: throw TaskByIdNotFoundException(taskId)
    }

    fun updateTaskStatus(taskId: Long, status: TaskStatus): Task {
        return taskStorage.updateTaskStatus(taskId, status)
    }

    fun startTask(taskId: Long): Task {
        return taskStorage.updateTaskStatus(taskId, TaskStatus.IN_PROCESS)
    }


    fun completeTask(userId: Long, taskId: Long): CompletedUserTask {
        return completedUserTaskStorage.createCompletedUserTask(userId, taskId)
    }

    fun getCompletedTasksByUserId(userId: Long): List<CompletedUserTask> {
        return completedUserTaskStorage.getCompletedUserTasksByUserId(userId)
    }
}