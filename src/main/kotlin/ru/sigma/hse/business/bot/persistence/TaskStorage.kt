package ru.sigma.hse.business.bot.persistence

import ru.sigma.hse.business.bot.domain.model.Task
import ru.sigma.hse.business.bot.domain.model.EventStatus

interface TaskStorage {
    fun getTask(id: Long): Task?

    fun createTask(
        name: String,
        description: String,
        points: Int
    ): Task

    fun updateTaskStatus(
        id: Long,
        isAvailable: Boolean
    ): Task

    fun deleteTask(id: Long)
}