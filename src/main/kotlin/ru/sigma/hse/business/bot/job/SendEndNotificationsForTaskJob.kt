package ru.sigma.hse.business.bot.job

import io.github.oshai.kotlinlogging.KotlinLogging
import org.quartz.DisallowConcurrentExecution
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.quartz.PersistJobDataAfterExecution
import org.springframework.stereotype.Component
import ru.sigma.hse.business.bot.notification.model.BeRealEndedNotification
import ru.sigma.hse.business.bot.service.MailingService

@Component
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
class SendEndNotificationsForTaskJob(
    private val mailingService: MailingService,
) : Job {
    override fun execute(context: JobExecutionContext) {
        val beRealEndedNotification = BeRealEndedNotification()
        mailingService.sendMailing(beRealEndedNotification)
        logger.info { "Send notifications for end be real task job" }
    }

    companion object {
        private val logger = KotlinLogging.logger {}
    }
}