package ru.sigma.hse.business.bot.job

import io.github.oshai.kotlinlogging.KotlinLogging
import org.quartz.DisallowConcurrentExecution
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.quartz.PersistJobDataAfterExecution
import org.springframework.stereotype.Component
import ru.sigma.hse.business.bot.notification.NotificationService
import ru.sigma.hse.business.bot.notification.model.ConferenceCompleteNotification
import ru.sigma.hse.business.bot.persistence.UserStorage

@Component
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
class SendConferenceCompleteNotificationJob(
    private val userStorage: UserStorage,
    private val notificationService: NotificationService,
) : Job {
    override fun execute(context: JobExecutionContext) {
        val userId = context.jobDetail.jobDataMap.getLongValue("userId")
        val telegramId = userStorage.getTelegramIdsByIds(listOf(userId)).first()
        val notification = ConferenceCompleteNotification()
        notificationService.notify(telegramId, notification)
        logger.info { "Notification about completing conference sent to $telegramId" }
    }

    companion object {
        private val logger = KotlinLogging.logger {  }
    }
}