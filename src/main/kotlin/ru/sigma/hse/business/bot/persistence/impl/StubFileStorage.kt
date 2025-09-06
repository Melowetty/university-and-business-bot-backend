package ru.sigma.hse.business.bot.persistence.impl

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import ru.sigma.hse.business.bot.persistence.FileStorage

@Component
@ConditionalOnProperty(name = ["integrations.yandex-disk.token"], havingValue = "", matchIfMissing = true)
class StubFileStorage : FileStorage {
    override fun save(path: String, file: ByteArray) {
        logger.info { "Stub saving file to $path" }
    }

    companion object {
        private val logger = KotlinLogging.logger {  }
    }
}