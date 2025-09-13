package ru.sigma.hse.business.bot.persistence

import ru.sigma.hse.business.bot.domain.model.CompletedUserTask
import java.time.LocalTime

interface CompletedUserTaskStorage {
    fun getCompletedUserTask(id: Long): CompletedUserTask?

    fun createCompletedUserTask(
        userId: Long,
        taskId: Long,
    ): CompletedUserTask

    fun deleteCompletedUserTask(id: Long)

    fun getCompletedUserTasksByUserId(userId: Long): List<CompletedUserTask>

    fun existUserCompleteActivity(userId: Long, taskId: Long): Boolean
}