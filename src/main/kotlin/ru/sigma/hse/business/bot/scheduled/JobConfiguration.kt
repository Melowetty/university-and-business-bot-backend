package ru.sigma.hse.business.bot.scheduled

import org.quartz.CronScheduleBuilder
import org.quartz.JobBuilder
import org.quartz.JobDetail
import org.quartz.Scheduler
import org.quartz.Trigger
import org.quartz.TriggerBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.support.JdbcTransactionManager
import org.springframework.scheduling.quartz.SchedulerFactoryBean

@Configuration
class JobConfiguration {
    @Value("\${jobs.collect-data-to-yandex-disk}")
    private lateinit var collectDataToYaDiskCron: String

    @Bean
    fun collectDataToYaDiskJobDetail(): JobDetail {
        return JobBuilder
            .newJob(CollectDataToYaDiskJob::class.java).withIdentity("CollectDataToYaDiskJob")
            .requestRecovery(true)
            .storeDurably()
            .build()
    }

    @Bean
    fun collectDataToYaDiskJobTrigger(
        someJobDetail: JobDetail
    ): Trigger {
        return TriggerBuilder.newTrigger().forJob(someJobDetail)
            .withIdentity("CollectDataToYaDiskJobTrigger")
            .withSchedule(CronScheduleBuilder.cronSchedule(collectDataToYaDiskCron))
            .build()

    }

    @Bean
    fun scheduler(
        triggers: List<Trigger>,
        factory: SchedulerFactoryBean
    ): Scheduler {
        factory.setWaitForJobsToCompleteOnShutdown(true)
        val scheduler = factory.scheduler
        factory.setOverwriteExistingJobs(true)
        factory.setTransactionManager(JdbcTransactionManager())
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