package ru.sigma.hse.business.bot.job

import io.github.oshai.kotlinlogging.KotlinLogging
import org.quartz.DisallowConcurrentExecution
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.springframework.stereotype.Component
import ru.sigma.hse.business.bot.notification.impl.BatchNotificationService
import ru.sigma.hse.business.bot.notification.model.ActivityEventNotification
import ru.sigma.hse.business.bot.persistence.UserStorage
import ru.sigma.hse.business.bot.persistence.VisitStorage
import ru.sigma.hse.business.bot.service.ActivityEventService
import ru.sigma.hse.business.bot.utils.Paginator

@Component
@DisallowConcurrentExecution
class SendNotificationsForActivityEventJob(
    private val activityEventService: ActivityEventService,
    private val visitStorage: VisitStorage,
    private val batchNotificationService: BatchNotificationService,
    private val userStorage: UserStorage,
) : Job {
    override fun execute(context: JobExecutionContext) {
        val activityId = context.mergedJobDataMap.getLongValue("activityId")
        val event = activityEventService.getEvent(activityId)
        val notification = ActivityEventNotification(event)

        Paginator.fetchPageable(
            limit = 200,
            fetchFunction = { limit, token ->
                visitStorage.getVisitsByTargetId(activityId, limit, token)
            }
        ) { visits ->
            logger.info { "Fetch batch visits for event $activityId to send activity event notification" }
            val userIds = visits.map { it.userId }
            val telegramIds = userStorage.getTelegramIdsByIds(userIds)
            batchNotificationService.notify(telegramIds, notification)
        }
        logger.info { "Send notifications for activity event job" }
    }

    companion object {
        private val logger = KotlinLogging.logger {}
    }
}