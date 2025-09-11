package ru.sigma.hse.business.bot.job

import io.github.oshai.kotlinlogging.KotlinLogging
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.springframework.stereotype.Component

@Component
class SendStartNotificationsForTempTaskJob : Job {
    override fun execute(context: JobExecutionContext) {
        logger.info { "Send notifications for start temp task job" }
    }

    companion object {
        private val logger = KotlinLogging.logger {}
    }
}