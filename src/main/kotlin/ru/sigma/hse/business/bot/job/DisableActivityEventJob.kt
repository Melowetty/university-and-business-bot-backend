package ru.sigma.hse.business.bot.job

import io.github.oshai.kotlinlogging.KotlinLogging
import org.quartz.DisallowConcurrentExecution
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.springframework.stereotype.Component
import ru.sigma.hse.business.bot.service.ActivityEventService

@Component
@DisallowConcurrentExecution
class DisableActivityEventJob(
    private val activityEventService: ActivityEventService,
): Job {
    override fun execute(context: JobExecutionContext) {
        val activityId = context.mergedJobDataMap.getLong("activityId")
        activityEventService.endEvent(activityId)
        logger.info { "Disabled activity event $activityId" }
    }

    companion object {
        private val logger = KotlinLogging.logger {  }
    }
}