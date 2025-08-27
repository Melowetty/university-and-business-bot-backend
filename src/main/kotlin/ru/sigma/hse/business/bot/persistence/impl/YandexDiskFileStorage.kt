package ru.sigma.hse.business.bot.persistence.impl

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.client.requiredBody
import ru.sigma.hse.business.bot.persistence.FileStorage

@Component
class YandexDiskFileStorage(
    @Qualifier("yandexDiskRestClient")
    private val diskRestClient: RestClient,
    private val basicRestClient: RestClient,
) : FileStorage {

    override fun save(path: String, file: ByteArray) {
        for (i in 1..3) {
            try {
                directSave(path, file)
                return
            } catch (e: Exception) {
                log.warn(e) { "Failed to save file to Yandex Disk: $path, retrying $i/3" }
            }
        }
    }

    private fun directSave(path: String, file: ByteArray) {
        log.info { "Saving file to Yandex Disk: $path with size ${file.size} bytes" }

        val fullPath = "app:/$path"
        val query = mapOf(
            "path" to fullPath,
            "overwrite" to "true"
        )

        val uploadResponse = diskRestClient.get()
            .uri("/uploads", query)
            .retrieve()
            .requiredBody<UploadResponse>()

        val url = uploadResponse.href

        basicRestClient.put()
            .uri(url)
            .body(file)
            .retrieve()
            .requiredBody<Unit>()

        log.info { "File saved to Yandex Disk: $path" }
    }

    private data class UploadResponse(
        val href: String
    )

    companion object {
        private val log = KotlinLogging.logger {  }
    }
}