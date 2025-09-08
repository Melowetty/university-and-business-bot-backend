package ru.sigma.hse.business.bot.persistence.impl.jdbc

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.transaction.Transactional
import kotlin.jvm.optionals.getOrNull
import org.springframework.stereotype.Component
import ru.sigma.hse.business.bot.domain.entity.TaskEntity
import ru.sigma.hse.business.bot.domain.model.Task
import ru.sigma.hse.business.bot.exception.task.TaskByIdNotFoundException
import ru.sigma.hse.business.bot.persistence.repository.TaskRepository

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
        description: String,
        points: Int
    ): Task {
        val taskEntity = TaskEntity(
            name = name,
            isAvailable = false,
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
        isAvailable: Boolean
    ): Task {
        if (!taskRepository.existsById(id)) {
            logger.warn { "Task with id $id does not exist" }
            throw TaskByIdNotFoundException(id)
        }

        taskRepository.updateTaskStatus(isAvailable, id)
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
    
    companion object {
        private val logger = KotlinLogging.logger {  }

        private fun TaskEntity.toTask(): Task {
            return Task(
                id = this.id(),
                name = name,
                isAvailable = isAvailable,
                description = description,
                points = points
            )
        }
    }
}