package ru.sigma.hse.business.bot.utils

import java.util.UUID

object RequestIdGenerator {
    fun generate(): String {
        return UUID.randomUUID().toString()
    }
}