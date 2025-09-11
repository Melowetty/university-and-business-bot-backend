package ru.sigma.hse.business.bot.job

import io.github.oshai.kotlinlogging.KotlinLogging
import org.quartz.DisallowConcurrentExecution
import org.quartz.JobExecutionContext
import org.quartz.PersistJobDataAfterExecution
import org.springframework.scheduling.quartz.QuartzJobBean
import org.springframework.stereotype.Component
import ru.sigma.hse.business.bot.persistence.FileStorage
import ru.sigma.hse.business.bot.persistence.Storage
import ru.sigma.hse.business.bot.utils.ExcelGenerator
import ru.sigma.hse.business.bot.utils.Paginator

@Component
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
class CollectDataToYaDiskJob(
    private val storage: Storage,
    private val fileStorage: FileStorage,
) : QuartzJobBean() {

    override fun executeInternal(context: JobExecutionContext) {
        collectDataAndUploadToYaDisk()
        logger.info { "Collected data and uploaded to YaDisk" }
    }

    private fun collectDataAndUploadToYaDisk() {
        val generator = ExcelGenerator()

        addUsers(generator)
        addVisits(generator)

        val path = "$YA_DISK_PATH/$EXCEL_NAME"
        fileStorage.save(path, generator.generate())
    }

    private fun addVisits(generator: ExcelGenerator) {
        Paginator.fetchPageable(
            fetchFunction = { limit, token ->
                storage.getVisits(limit, token)
            },
            startToken = 0,
            limit = 100
        ) {
            generator.addVisits(it)
        }
    }

    private fun addUsers(generator: ExcelGenerator) {
        Paginator.fetchPageable(
            fetchFunction = { limit, token ->
                storage.getUsers(limit, token)
            },
            startToken = 0,
            limit = 100
        ) {
            generator.addUsers(it)
        }
    }

    companion object {
        private const val YA_DISK_PATH = "Отчёты"
        private const val EXCEL_NAME = "Отчёт.xlsx"

        private val logger = KotlinLogging.logger {  }
    }
}