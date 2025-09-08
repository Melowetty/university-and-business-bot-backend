package ru.sigma.hse.business.bot.persistence.impl

import io.github.oshai.kotlinlogging.KotlinLogging
import ru.sigma.hse.business.bot.persistence.FileStorage

class StubFileStorage : FileStorage {
    override fun save(path: String, file: ByteArray) {
        logger.info { "Stub saving file to $path" }
    }

    companion object {
        private val logger = KotlinLogging.logger {  }
    }
}