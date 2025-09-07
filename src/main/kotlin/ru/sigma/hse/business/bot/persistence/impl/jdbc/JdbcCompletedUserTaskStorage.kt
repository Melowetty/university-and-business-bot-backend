package ru.sigma.hse.business.bot.persistence.impl.jdbc

import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.LocalTime
import org.springframework.stereotype.Component
import ru.sigma.hse.business.bot.domain.entity.CompletedUserTaskEntity
import ru.sigma.hse.business.bot.domain.model.CompletedUserTask
import ru.sigma.hse.business.bot.persistence.repository.CompletedUserTaskRepository

@Component
class JdbcCompletedUserTaskStorage(
    private val completedUserTaskRepository: CompletedUserTaskRepository
) {
    fun getCompletedUserTask(id: Long): CompletedUserTask? {
        if (completedUserTaskRepository.existsById(id)) {
            logger.info { "Found completed user task with id $id" }
            return completedUserTaskRepository.findById(id).get().toCompletedUserTask()
        }

        logger.warn { "Completed user task with id $id does not exist" }
        return null
    }

    fun createCompletedUserTask(
        userId: Long,
        taskId: Long,
    ): CompletedUserTask {
        val completedUserTaskEntity = CompletedUserTaskEntity(
            userId = userId,
            taskId = taskId,
            timeTaken = LocalTime.now()
        )

        val savedEntity = completedUserTaskRepository.save(completedUserTaskEntity)
        logger.info { "Created completed user tasks with id ${savedEntity.id()}" }
        return savedEntity.toCompletedUserTask()
    }

    fun deleteCompletedUserTask(id: Long) {
        if (!completedUserTaskRepository.existsById(id)) {
            logger.warn { "Completed user task with id $id does not exist" }
            throw NoSuchElementException("Completed user task with id $id does not exist")
        }

        completedUserTaskRepository.deleteById(id)
        logger.info { "Deleted completed user task with id $id" }
    }

    fun getCompletedUserTasksByUserId(userId: Long): List<CompletedUserTask> {
        return completedUserTaskRepository.findByUserId(userId).map {it.toCompletedUserTask()}
    }
    
    companion object {
        private val logger = KotlinLogging.logger {  }

        private fun CompletedUserTaskEntity.toCompletedUserTask(): CompletedUserTask {
            return CompletedUserTask(
                id = this.id(),
                userId = this.userId,
                taskId = this.taskId,
                timeTaken = this.timeTaken
            )
        }
    }
}