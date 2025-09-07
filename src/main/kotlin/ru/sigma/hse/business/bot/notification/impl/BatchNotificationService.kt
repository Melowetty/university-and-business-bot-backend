package ru.sigma.hse.business.bot.notification.impl

import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors
import org.springframework.stereotype.Service
import ru.sigma.hse.business.bot.notification.NotificationService
import ru.sigma.hse.business.bot.notification.base.Notification

@Service
class BatchNotificationService(
    private val notificationService: NotificationService
) {
    private val executor = Executors.newFixedThreadPool(50)

    fun notify(telegramIds: List<Long>, notification: Notification) {
        val tasks = telegramIds.map { telegramId ->
            CompletableFuture.supplyAsync({
                notificationService.notify(telegramId, notification)
            }, executor
            ).exceptionally {
                logger.error(it) { "Failed to send batch notification to $telegramId" }
            }
        }

        CompletableFuture.allOf(*tasks.toTypedArray()).join()
    }

    companion object {
        private val logger = KotlinLogging.logger {  }
    }
}