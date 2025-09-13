package ru.sigma.hse.business.bot.persistence.impl.jdbc

import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.LocalDateTime
import kotlin.jvm.optionals.getOrNull
import org.springframework.stereotype.Component
import ru.sigma.hse.business.bot.domain.entity.CompletedUserTaskEntity
import ru.sigma.hse.business.bot.domain.model.CompletedUserTask
import ru.sigma.hse.business.bot.exception.task.CompletedUserTaskByIdNotFoundException
import ru.sigma.hse.business.bot.persistence.repository.CompletedUserTaskRepository

@Component
class JdbcCompletedUserTaskStorage(
    private val completedUserTaskRepository: CompletedUserTaskRepository
) {
    fun getCompletedUserTask(id: Long): CompletedUserTask? {
        val task = completedUserTaskRepository.findById(id).getOrNull()
            ?: run {
                logger.warn { "Completed user task with id $id does not exist" }
                return null
            }

        logger.info { "Found completed user task with id $id" }
        return task.toCompletedUserTask()
    }

    fun createCompletedUserTask(
        userId: Long,
        taskId: Long,
    ): CompletedUserTask {
        val completedUserTaskEntity = CompletedUserTaskEntity(
            userId = userId,
            taskId = taskId,
            completeTime = LocalDateTime.now(),
        )

        val savedEntity = completedUserTaskRepository.save(completedUserTaskEntity)
        logger.info { "Created completed user tasks with id ${savedEntity.id()}" }
        return savedEntity.toCompletedUserTask()
    }

    fun deleteCompletedUserTask(id: Long) {
        if (!completedUserTaskRepository.existsById(id)) {
            logger.warn { "Completed user task with id $id does not exist" }
            throw CompletedUserTaskByIdNotFoundException(id)
        }

        completedUserTaskRepository.deleteById(id)
        logger.info { "Deleted completed user task with id $id" }
    }

    fun getCompletedUserTasksByUserId(userId: Long): List<CompletedUserTask> {
        return completedUserTaskRepository.findByUserId(userId).map {it.toCompletedUserTask()}
    }

    fun getByUserIdTaskId(userId: Long, taskId: Long): Boolean {
        return completedUserTaskRepository.existByUserIdTaskId(userId, taskId)
    }

    companion object {
        private val logger = KotlinLogging.logger {  }

        private fun CompletedUserTaskEntity.toCompletedUserTask(): CompletedUserTask {
            return CompletedUserTask(
                id = this.id(),
                userId = this.userId,
                taskId = this.taskId,
                completeTime = this.completeTime,
            )
        }
    }
}