package ru.sigma.hse.business.bot.job

import org.quartz.DisallowConcurrentExecution
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.springframework.stereotype.Component
import ru.sigma.hse.business.bot.service.TaskService

@Component
@DisallowConcurrentExecution
class DisableTaskJob(
    private val taskService: TaskService
) : Job {
    override fun execute(context: JobExecutionContext) {
        val taskId = context.mergedJobDataMap.getLongValue("taskId")
        taskService.endTask(taskId)
    }
}