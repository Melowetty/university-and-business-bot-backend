package ru.sigma.hse.business.bot.scheduled

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import ru.sigma.hse.business.bot.persistence.FileStorage
import ru.sigma.hse.business.bot.persistence.Storage
import ru.sigma.hse.business.bot.utils.ExcelGenerator
import ru.sigma.hse.business.bot.utils.Paginator

@Component
class CollectDataToYaDiskJob(
    private val storage: Storage,
    private val fileStorage: FileStorage,
) {
    @Scheduled(cron = "0 0/5 * * * ?")
    fun collectDataAndUploadToYaDisk() {
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
    }
}