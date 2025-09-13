package ru.sigma.hse.business.bot.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import ru.sigma.hse.business.bot.api.controller.model.task.CreateTaskRequest
import ru.sigma.hse.business.bot.domain.model.CompletedUserTask
import ru.sigma.hse.business.bot.domain.model.Task
import ru.sigma.hse.business.bot.domain.model.TaskStatus
import ru.sigma.hse.business.bot.domain.model.TaskType
import ru.sigma.hse.business.bot.exception.base.BadArgumentException
import ru.sigma.hse.business.bot.exception.completed.AlreadyExistsCompletedUserTaskException
import ru.sigma.hse.business.bot.exception.task.TaskByIdNotFoundException
import ru.sigma.hse.business.bot.job.DisableTaskJob
import ru.sigma.hse.business.bot.job.SendEndNotificationsForTaskJob
import ru.sigma.hse.business.bot.job.SendStartNotificationsForTaskJob
import ru.sigma.hse.business.bot.persistence.CompletedUserTaskStorage
import ru.sigma.hse.business.bot.persistence.TaskStorage
import java.time.Duration


@Service
class TaskService(
    private val taskStorage: TaskStorage,
    private val completedUserTaskStorage: CompletedUserTaskStorage,
    private val scheduleJobService: ScheduleJobService,
    private val userService: UserService,
) {
    fun createTask(request: CreateTaskRequest): Task {
        return taskStorage.createTask(
            name = request.name,
            type = request.type,
            description = request.description,
            points = request.points,
            duration = request.duration?.let { Duration.ofMinutes(it) }
        )
    }

    fun getTask(taskId: Long): Task {
        return taskStorage.getTask(taskId)
            ?: throw TaskByIdNotFoundException(taskId)
    }

    @Transactional
    fun startTask(taskId: Long): Task {
        val task = taskStorage.getTask(taskId)
            ?: throw TaskByIdNotFoundException(taskId)

        if (task.status != TaskStatus.READY) {
            throw BadArgumentException("ILLEGAL_TASK_STATUS", "Task status is not ready")
        }

        val newTask = taskStorage.updateTaskStatus(taskId, TaskStatus.IN_PROCESS)

        if (newTask.type == TaskType.TEMP) {
            val payload = mapOf("taskId" to taskId.toString())
            scheduleJobService.runImmediatelyJob(payload, SendStartNotificationsForTaskJob::class.java)
            scheduleJobService.scheduleJob(newTask.duration!!, payload, DisableTaskJob::class.java)
        }

        return newTask
    }

    @Transactional
    fun endTask(taskId: Long): Task {
        val task = taskStorage.getTask(taskId)
            ?: throw TaskByIdNotFoundException(taskId)

        if (task.status != TaskStatus.IN_PROCESS) {
            throw BadArgumentException("ILLEGAL_TASK_STATUS", "Task status is not started")
        }

        val newTask = taskStorage.updateTaskStatus(taskId, TaskStatus.FINISHED)

        if (newTask.type == TaskType.TEMP) {
            val payload = mapOf("taskId" to taskId.toString())
            scheduleJobService.runImmediatelyJob(payload, SendEndNotificationsForTaskJob::class.java)
        }

        return newTask
    }

    @Transactional
    fun endAllTasks() {
        taskStorage.getRanTasks().forEach {
            endTask(it.id)
        }
    }

    @Transactional
    fun completeTask(userId: Long, taskId: Long): CompletedUserTask {
        val user = userService.getUser(userId)
        val task = taskStorage.getTask(taskId)
            ?:throw TaskByIdNotFoundException(taskId)

        if (completedUserTaskStorage.existUserCompleteActivity(userId, taskId)){
            throw AlreadyExistsCompletedUserTaskException(userId, taskId)
        }

        userService.addPointsToUserScore(user.id, task.points)

        return completedUserTaskStorage.createCompletedUserTask(userId, taskId)
    }
}