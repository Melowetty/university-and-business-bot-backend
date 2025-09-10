package ru.sigma.hse.business.bot.persistence

import ru.sigma.hse.business.bot.domain.model.Task
import ru.sigma.hse.business.bot.domain.model.TaskStatus
import ru.sigma.hse.business.bot.domain.model.TaskType
import java.time.Duration

interface TaskStorage {
    fun getTask(id: Long): Task?

    fun createTask(
        name: String,
        type: TaskType,
        description: String,
        points: Int,
        duration: Duration
    ): Task

    fun updateTaskStatus(
        id: Long,
        status: TaskStatus
    ): Task

    fun deleteTask(id: Long)
}