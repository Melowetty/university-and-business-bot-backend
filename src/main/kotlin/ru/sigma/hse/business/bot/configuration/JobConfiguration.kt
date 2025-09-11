package ru.sigma.hse.business.bot.configuration

import org.quartz.CronScheduleBuilder
import org.quartz.JobBuilder
import org.quartz.JobDetail
import org.quartz.Scheduler
import org.quartz.Trigger
import org.quartz.TriggerBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.quartz.SchedulerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import ru.sigma.hse.business.bot.job.CollectDataToFileStorage

@Configuration
class JobConfiguration {
    @Value("\${jobs.collect-data-to-file-storage}")
    private lateinit var collectDataToFileStorageCron: String

    @Bean
    fun collectDataToFileStorageJobDetail(): JobDetail {
        return JobBuilder
            .newJob(CollectDataToFileStorage::class.java).withIdentity("CollectDataToFileStorageJob")
            .requestRecovery(true)
            .storeDurably()
            .build()
    }

    @Bean
    fun collectDataToFileStorageJobTrigger(): Trigger {
        return TriggerBuilder.newTrigger().forJob(collectDataToFileStorageJobDetail())
            .withIdentity("CollectDataToFileStorageJobTrigger")
            .withSchedule(CronScheduleBuilder.cronSchedule(collectDataToFileStorageCron))
            .build()

    }

    @Bean
    fun scheduler(
        triggers: List<Trigger>,
        factory: SchedulerFactoryBean,
        transactionManager: PlatformTransactionManager
    ): Scheduler {
        factory.setWaitForJobsToCompleteOnShutdown(true)
        val scheduler = factory.scheduler
        factory.setOverwriteExistingJobs(true)
        factory.setTransactionManager(transactionManager)
        rescheduleTriggers(triggers, scheduler)
        scheduler.start()
        return scheduler
    }

    private fun rescheduleTriggers(
        triggers: List<Trigger>,
        scheduler: Scheduler
    ) {
        triggers.forEach {
            if (!scheduler.checkExists(it.key)) {
                scheduler.scheduleJob(it)
            } else {
                scheduler.rescheduleJob(it.key, it)
            }
        }
    }
}