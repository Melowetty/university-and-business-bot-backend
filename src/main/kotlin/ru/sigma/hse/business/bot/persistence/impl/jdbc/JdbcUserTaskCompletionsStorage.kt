package ru.sigma.hse.business.bot.persistence.impl.jdbc

import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.LocalTime
import org.springframework.stereotype.Component
import ru.sigma.hse.business.bot.domain.entity.UserTaskCompletionsEntity
import ru.sigma.hse.business.bot.domain.model.UserTaskCompletions
import ru.sigma.hse.business.bot.domain.model.EventStatus
import ru.sigma.hse.business.bot.persistence.repository.UserTaskCompletionsRepository

@Component
class JdbcUserTaskCompletionsStorage(
    private val userTaskCompletionsRepository: UserTaskCompletionsRepository
) {
    fun getUserTaskCompletions(id: Long): UserTaskCompletions? {
        if (userTaskCompletionsRepository.existsById(id)) {
            logger.info { "Found user task completions with id $id" }
            return userTaskCompletionsRepository.findById(id).get().toUserTaskCompletions()
        }

        logger.warn { "User task completions with id $id does not exist" }
        return null
    }

    fun createUserTaskCompletions(
        userId: Long,
        taskId: Long,
        timeTaken: LocalTime
    ): UserTaskCompletions {
        val userTaskCompletionsEntity = UserTaskCompletionsEntity(
            userId = userId,
            taskId = taskId,
            timeTaken = timeTaken
        )

        val savedEntity = userTaskCompletionsRepository.save(userTaskCompletionsEntity)
        logger.info { "Created user task completions with id ${savedEntity.id()}" }
        return savedEntity.toUserTaskCompletions()
    }

    fun deleteUserTaskCompletions(id: Long) {
        if (!userTaskCompletionsRepository.existsById(id)) {
            logger.warn { "User task completions with id $id does not exist" }
            throw NoSuchElementException("User task completions with id $id does not exist")
        }

        userTaskCompletionsRepository.deleteById(id)
        logger.info { "Deleted user task completions with id $id" }
    }
    
    companion object {
        private val logger = KotlinLogging.logger {  }

        private fun UserTaskCompletionsEntity.toUserTaskCompletions(): UserTaskCompletions {
            return UserTaskCompletions(
                id = this.id(),
                userId = this.userId,
                taskId = this.taskId,
                timeTaken = this.timeTaken
            )
        }
    }
}