package ru.sigma.hse.business.bot.persistence.impl.jdbc

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.transaction.Transactional
import kotlin.jvm.optionals.getOrNull
import org.springframework.stereotype.Component
import ru.sigma.hse.business.bot.domain.entity.TaskEntity
import ru.sigma.hse.business.bot.domain.model.Task
import ru.sigma.hse.business.bot.domain.model.TaskStatus
import ru.sigma.hse.business.bot.domain.model.TaskType
import ru.sigma.hse.business.bot.exception.task.TaskByIdNotFoundException
import ru.sigma.hse.business.bot.persistence.repository.TaskRepository
import java.time.Duration

@Component
class JdbcTaskStorage(
    private val taskRepository: TaskRepository
) {
    fun getTask(id: Long): Task? {
        val task = taskRepository.findById(id).getOrNull()
            ?: run {
                logger.warn { "Task with id $id does not exist" }
                return null
            }

        logger.info { "Found task with id $id" }
        return task.toTask()
    }

    fun createTask(
        name: String,
        type: TaskType,
        description: String,
        points: Int,
        duration: Duration
    ): Task {
        val taskEntity = TaskEntity(
            name = name,
            type = type,
            duration = duration,
            status = TaskStatus.READY,
            description = description,
            points = points
        )

        val savedEntity = taskRepository.save(taskEntity)
        logger.info { "Created task with id ${savedEntity.id()}" }
        return savedEntity.toTask()
    }

    @Transactional
    fun updateTaskStatus(
        id: Long,
        status: TaskStatus
    ): Task {
        if (!taskRepository.existsById(id)) {
            logger.warn { "Task with id $id does not exist" }
            throw TaskByIdNotFoundException(id)
        }

        taskRepository.updateTaskStatus(status, id)
        logger.info { "Updated task with id $id" }
        return getTask(id)!!
    }

    fun deleteTask(id: Long) {
        if (!taskRepository.existsById(id)) {
            logger.warn { "Task with id $id does not exist" }
            throw TaskByIdNotFoundException(id)
        }

        taskRepository.deleteById(id)
        logger.info { "Deleted task with id $id" }
    }

    fun startTask(
        id: Long,
    ): Task {
        if (!taskRepository.existsById(id)) {
            logger.warn { "Task with id $id does not exist" }
            throw TaskByIdNotFoundException(id)

        }

        taskRepository.updateTaskStatus(TaskStatus.IN_PROCESS, id)
        logger.info { "Updated task status with id $id" }
        return getTask(id)!!
    }

    companion object {
        private val logger = KotlinLogging.logger {  }

        private fun TaskEntity.toTask(): Task {
            return Task(
                id = this.id(),
                name = name,
                type = type,
                duration = duration,
                status = status,
                description = description,
                points = points
            )
        }
    }
}