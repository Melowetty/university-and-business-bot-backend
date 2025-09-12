package ru.sigma.hse.business.bot.service

import org.springframework.stereotype.Service
import ru.sigma.hse.business.bot.api.controller.model.task.UserTask
import ru.sigma.hse.business.bot.api.controller.model.task.UserTaskStatus
import ru.sigma.hse.business.bot.api.controller.model.task.UserTaskType
import ru.sigma.hse.business.bot.domain.model.Task
import ru.sigma.hse.business.bot.domain.model.TaskStatus
import ru.sigma.hse.business.bot.domain.model.TaskType
import ru.sigma.hse.business.bot.exception.task.TaskByIdNotFoundException
import ru.sigma.hse.business.bot.exception.task.TaskCannotBeSubmittedException
import ru.sigma.hse.business.bot.persistence.CompletedUserTaskStorage
import ru.sigma.hse.business.bot.persistence.TaskStorage

@Service
class UserTaskService(
    private val taskStorage: TaskStorage,
    private val completedUserTaskStorage: CompletedUserTaskStorage,
) {
    fun getAvailableUserTasks(userId: Long): List<UserTask> {
        val tasks = taskStorage.getRanTasks()
        val completedTasks = completedUserTaskStorage.getCompletedUserTasksByUserId(userId)
            .map { it.taskId }.toSet()

        return tasks.map {
            it.toUserTask(isCompleted = it.id in completedTasks)
        }
    }

    fun getTask(userId: Long, taskId: Long): UserTask {
        val task = taskStorage.getTask(taskId)
            ?: throw TaskByIdNotFoundException(taskId)

        if (task.status != TaskStatus.IN_PROCESS) {
            throw TaskCannotBeSubmittedException()
        }

        val completedTasks = completedUserTaskStorage.getCompletedUserTasksByUserId(userId)
            .map { it.taskId }.toSet()

        return task.toUserTask(isCompleted = taskId in completedTasks)
    }

    private fun Task.toUserTask(isCompleted: Boolean): UserTask {
        val userTaskStatus = if (isCompleted) UserTaskStatus.DONE else UserTaskStatus.IN_PROGRESS

        val taskType = when(type) {
            TaskType.PERMANENT -> UserTaskType.BASIC_TASK
            TaskType.TEMP -> UserTaskType.BE_REAL
        }

        return UserTask(
            id = id,
            name = name,
            description = description,
            isAvailable = true,
            status = userTaskStatus,
            taskType = taskType,
        )
    }
}