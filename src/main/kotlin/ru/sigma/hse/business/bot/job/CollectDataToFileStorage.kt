package ru.sigma.hse.business.bot.job

import io.github.oshai.kotlinlogging.KotlinLogging
import org.quartz.DisallowConcurrentExecution
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.springframework.stereotype.Component
import ru.sigma.hse.business.bot.persistence.FileStorage
import ru.sigma.hse.business.bot.persistence.Storage
import ru.sigma.hse.business.bot.utils.ExcelGenerator
import ru.sigma.hse.business.bot.utils.Paginator

@Component
@DisallowConcurrentExecution
class CollectDataToFileStorage(
    private val storage: Storage,
    private val fileStorage: FileStorage,
) : Job {

    override fun execute(context: JobExecutionContext) {
        collectDataAndUploadToFileStorage()
        logger.info { "Collected data and uploaded to file storage" }
    }

    private fun collectDataAndUploadToFileStorage() {
        val generator = ExcelGenerator()

        addUsers(generator)
        addVisits(generator)

        val path = "$FILE_PATH/$EXCEL_NAME"
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
        private const val FILE_PATH = "Отчёты"
        private const val EXCEL_NAME = "Отчёт.xlsx"

        private val logger = KotlinLogging.logger {  }
    }
}