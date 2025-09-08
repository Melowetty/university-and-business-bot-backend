package ru.sigma.hse.business.bot.notification.impl

import io.github.oshai.kotlinlogging.KotlinLogging
import ru.sigma.hse.business.bot.notification.NotificationService
import ru.sigma.hse.business.bot.notification.base.Notification

class StubNotificationService : NotificationService {
    override fun notify(
        telegramId: Long,
        notification: Notification
    ) {
        logger.info { "Stub notify for user $telegramId with notification ${notification.javaClass.simpleName}" }
    }

    companion object {
        private val logger = KotlinLogging.logger {  }
    }
}