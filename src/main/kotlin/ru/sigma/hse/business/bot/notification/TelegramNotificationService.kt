package ru.sigma.hse.business.bot.notification

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException
import ru.sigma.hse.business.bot.exception.RetryableException
import ru.sigma.hse.business.bot.notification.base.Notification
import ru.sigma.hse.business.bot.notification.base.NotificationProcessor

@Service
class TelegramNotificationService(
    @Value("\${integrations.telegram.token}")
    botToken: String,
    processors: List<NotificationProcessor<out Notification>>
) {
    private val telegramBot = OkHttpTelegramClient(botToken)

    private val processorMap: Map<Class<out Notification>, NotificationProcessor<out Notification>> =
        processors.associateBy { it.getNotificationType() }

    fun notify(telegramId: Long, notification: Notification) {
        val processor = getProcessor(notification)
            ?: run {
                logger.warn { "No processor for notification ${notification.javaClass.simpleName}" }
                return
            }

        internalProcess(processor, telegramId, notification)
        logger.info { "Sent ${notification.javaClass.simpleName} notification to $telegramId" }
    }

    @Retryable(
        retryFor = [RetryableException::class],
        maxAttempts = 3,
        backoff = Backoff(delay = 1000)
    )
    private fun internalProcess(processor: NotificationProcessor<Notification>, telegramId: Long, notification: Notification) {
        try {
            processor.process(telegramBot, telegramId, notification)
        } catch (e: TelegramApiRequestException) {
            if (e.errorCode == 429) {
                logger.warn(e) { "Failed to send ${notification.javaClass.simpleName} notification to $telegramId because of too many requests. Retry request" }
                throw RetryableException(e)
            }

            logger.error(e) { "Failed to send ${notification.javaClass.simpleName} notification to $telegramId" }
            throw e
        }
    }

    fun <T : Notification> getProcessor(notification: T): NotificationProcessor<T>? {
        return processorMap[notification::class.java] as? NotificationProcessor<T>
    }

    companion object {
        private val logger = KotlinLogging.logger {  }
    }
}