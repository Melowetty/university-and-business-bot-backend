package ru.sigma.hse.business.bot.persistence.impl

import org.springframework.stereotype.Component
import ru.sigma.hse.business.bot.persistence.FileStorage

@Component
class YandexDiskFileStorage : FileStorage {
    override fun save(path: String, file: ByteArray) {
        TODO("Not yet implemented")
    }
}