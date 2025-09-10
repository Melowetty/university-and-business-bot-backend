package ru.sigma.hse.business.bot.service

import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.Duration
import java.time.LocalDateTime
import org.quartz.DateBuilder
import org.quartz.Job
import org.quartz.JobBuilder
import org.quartz.JobDataMap
import org.quartz.Scheduler
import org.quartz.Trigger
import org.quartz.TriggerBuilder
import org.springframework.stereotype.Service

@Service
class ScheduleJobService(
    private val scheduler: Scheduler,
) {
    fun scheduleJob(duration: Duration, payload: Map<String, String>, target: Class<out Job>) {
        val hash = payload.hashCode()
        val identity = target.simpleName
        val timestamp = System.currentTimeMillis()
        val jobKey = "$identity-$hash-$timestamp"
        val jobDetail = JobBuilder.newJob(target)
            .withIdentity(jobKey)
            .usingJobData(JobDataMap(payload))
            .storeDurably()
            .requestRecovery()
            .build()

        val date = LocalDateTime.now().plus(duration)
        val triggerKey = "${identity}Trigger-$hash-$timestamp"

        val trigger = TriggerBuilder.newTrigger()
            .withIdentity(triggerKey)
            .startAt(DateBuilder.dateOf(date.hour, date.minute, date.second))
            .forJob(jobKey)
            .build()

        scheduler.scheduleJob(jobDetail, trigger)

        logger.info { "Scheduled job $jobKey at $date" }
    }

    fun runImmediatelyJob(payload: Map<String, String>, target: Class<out Job>) {
        val hash = payload.hashCode()
        val identity = target.simpleName
        val timestamp = System.currentTimeMillis()
        val jobKey = "$identity-$hash-$timestamp"
        val jobDetail = JobBuilder.newJob(target)
            .withIdentity(jobKey)
            .usingJobData(JobDataMap(payload))
            .storeDurably()
            .requestRecovery()
            .build()

        val triggerKey = "${identity}Trigger-$timestamp"

        val instantTrigger =TriggerBuilder.newTrigger()
            .withIdentity(triggerKey)
            .startNow()
            .forJob(jobKey)
            .withPriority(Trigger.DEFAULT_PRIORITY)
            .build()

        scheduler.scheduleJob(jobDetail, instantTrigger)

        logger.info { "Scheduled immediately job $jobKey" }
    }

    companion object {
        private val logger = KotlinLogging.logger {}
    }
}