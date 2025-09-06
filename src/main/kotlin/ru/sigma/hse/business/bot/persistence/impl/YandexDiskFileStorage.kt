package ru.sigma.hse.business.bot.persistence.impl

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.client.RestClient
import org.springframework.web.client.body
import ru.sigma.hse.business.bot.persistence.FileStorage

@Component
@ConditionalOnProperty(name = ["integrations.yandex-disk.token"], havingValue = ".+", matchIfMissing = false)
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
            } catch (e: HttpServerErrorException) {
                log.warn(e) { "Failed to save file to Yandex Disk: $path, retrying $i/3" }
            }
        }
    }

    private fun directSave(path: String, file: ByteArray) {
        log.info { "Saving file to Yandex Disk: $path with size ${file.size} bytes" }

        val fullPath = "app:/$path"

        createParentDirs(fullPath)

        val uploadResponse = diskRestClient.get()
            .uri { uri -> uri
                .path("/upload")
                .queryParam("path", fullPath)
                .queryParam("overwrite", true)
                .build()
            }
            .retrieve()
            .body<UploadResponse>()

        uploadResponse
            ?: throw IllegalStateException("Upload response is null")

        val url = uploadResponse.href

        basicRestClient.put()
            .uri(url)
            .body(file)
            .retrieve()
            .toBodilessEntity()

        log.info { "File saved to Yandex Disk: $path" }
    }

    private fun createParentDirs(path: String) {
        val parentPath = path.substringBeforeLast('/')
        log.info { "Creating parent directories for $path" }

        if (parentPath.isNotEmpty()) {
            try {
                diskRestClient.put()
                    .uri { uri ->
                        uri
                            .queryParam("path", parentPath)
                            .build()
                    }
                    .retrieve()
                    .toBodilessEntity()
            } catch (e: HttpClientErrorException) {
                if (e.statusCode == HttpStatus.CONFLICT) {
                    log.debug { "Parent dirs already exists" }
                } else {
                    throw e
                }
            }
        }

        log.info { "Parent directories created for $path" }
    }

    private data class UploadResponse(
        val href: String
    )

    companion object {
        private val log = KotlinLogging.logger {  }
    }
}