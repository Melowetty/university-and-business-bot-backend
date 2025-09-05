package ru.sigma.hse.business.bot.persistence.impl

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Primary
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.client.RestClient
import org.springframework.web.client.body
import ru.sigma.hse.business.bot.persistence.FileStorage
import java.io.File

@Component
@Primary
class LocalFileStorage : FileStorage {

    override fun save(path: String, file: ByteArray) {
        try {
            directSave(path, file)
            return
        } catch (e: Exception) {
            log.warn(e) { "Failed to save file to local path: $path" }
        }
    }

    private fun directSave(path: String, file: ByteArray) {
        log.info { "Saving file to local path with size ${file.size} bytes" }

        val savePath = File("examples")
        savePath.mkdirs()

        val savingFile = File(savePath,"qr_${path}.png")
        savingFile.writeBytes(file)

        log.info { "File saved to local path: $savePath$path" }
    }

    companion object {
        private val log = KotlinLogging.logger {  }
    }
}