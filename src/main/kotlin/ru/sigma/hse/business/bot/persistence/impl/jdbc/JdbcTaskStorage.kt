package ru.sigma.hse.business.bot.persistence.impl.jdbc

import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.LocalTime
import org.springframework.stereotype.Component
import ru.sigma.hse.business.bot.domain.entity.TaskEntity
import ru.sigma.hse.business.bot.domain.model.Task
import ru.sigma.hse.business.bot.domain.model.EventStatus
import ru.sigma.hse.business.bot.persistence.repository.TaskRepository

@Component
class JdbcTaskStorage(
    private val taskRepository: TaskRepository
) {
    fun getTask(id: Long): Task? {
        if (taskRepository.existsById(id)) {
            logger.info { "Found task with id $id" }
            return taskRepository.findById(id).get().toTask()
        }

        logger.warn { "Task with id $id does not exist" }
        return null
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

    fun updateTaskStatus(
        id: Long,
        isAvaiable: Boolean
    ): Task {
        if (!taskRepository.existsById(id)) {
            logger.warn { "Task with id ${id} does not exist" }
            throw NoSuchElementException("Task with id ${id} does not exist")
        }

        val existingTask = taskRepository.findById(id).get()
        val taskEntity = existingTask.apply {
            this.isAvailable = isAvailable
        }

        val savedEntity = taskRepository.save(taskEntity)
        logger.info { "Updated task with id ${taskEntity.id()}" }
        return savedEntity.toTask()
    }

    fun deleteTask(id: Long) {
        if (!taskRepository.existsById(id)) {
            logger.warn { "Task with id $id does not exist" }
            throw NoSuchElementException("Task with id $id does not exist")
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