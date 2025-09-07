package ru.sigma.hse.business.bot.persistence

import ru.sigma.hse.business.bot.domain.model.UserTaskCompletions
import java.time.LocalTime

interface UserTaskCompletionsStorage {
    fun getUserTaskCompletions(id: Long): UserTaskCompletions?

    fun createUserTaskCompletions(
        userId: Long,
        taskId: Long,
        timeTaken: LocalTime
    ): UserTaskCompletions

    fun deleteUserTaskCompletions(id: Long)
}