package ru.sigma.hse.business.bot.job

import io.github.oshai.kotlinlogging.KotlinLogging
import org.quartz.DisallowConcurrentExecution
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.quartz.PersistJobDataAfterExecution
import org.springframework.stereotype.Component
import ru.sigma.hse.business.bot.exception.task.TaskByIdNotFoundException
import ru.sigma.hse.business.bot.notification.model.BeRealStartedNotification
import ru.sigma.hse.business.bot.persistence.TaskStorage
import ru.sigma.hse.business.bot.service.MailingService

@Component
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
class SendStartNotificationsForTaskJob(
    private val mailingService: MailingService,
    private val taskStorage: TaskStorage,
) : Job {
    override fun execute(context: JobExecutionContext) {
        val taskId = context.mergedJobDataMap.getLongValue("taskId")
        val task = taskStorage.getTask(taskId)
            ?: throw TaskByIdNotFoundException(taskId)

        val beRealStartedNotification = BeRealStartedNotification(
            taskId = taskId,
            description = task.description,
            durationInMinutes = task.duration!!.toMinutesPart()
        )
        mailingService.sendMailing(beRealStartedNotification)
        logger.info { "Send notifications for start be real task job" }
    }

    companion object {
        private val logger = KotlinLogging.logger {}
    }
}