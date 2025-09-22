package ru.sigma.hse.business.bot.notification.impl

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException
import ru.sigma.hse.business.bot.exception.RetryableException
import ru.sigma.hse.business.bot.notification.NotificationService
import ru.sigma.hse.business.bot.notification.base.Notification
import ru.sigma.hse.business.bot.notification.base.NotificationProcessor

@Service
@ConditionalOnProperty(name = ["integrations.telegram.token"], havingValue = "")
class TelegramNotificationService(
    @Value("\${integrations.telegram.token}")
    botToken: String,
    processors: List<NotificationProcessor<out Notification>>
) : NotificationService {
    private val telegramBot = OkHttpTelegramClient(botToken)

    private val processorMap: Map<Class<out Notification>, NotificationProcessor<out Notification>> =
        processors.associateBy { it.getNotificationType() }

    @Deprecated("Remove 23.09.2025")
    private val whiteList = listOf(
        774471737L,
        823397841L,
        743056572L,
        351259027L,
        646596194L
    )

    override fun notify(telegramId: Long, notification: Notification) {
        val processor = getProcessor(notification)
            ?: run {
                logger.warn { "No processor for notification ${notification.javaClass.simpleName}" }
                return
            }

        @Deprecated("Remove 23.09.2025")
        if (!whiteList.contains(telegramId)) {
            logger.warn { "Notification ${notification.javaClass.simpleName} temporary is not allowed for $telegramId" }
            return
        }

        internalProcess(processor, telegramId, notification)
        logger.info { "Sent ${notification.javaClass.simpleName} notification to $telegramId" }
    }

    @Retryable(
        retryFor = [RetryableException::class],
        maxAttempts = 7,
        backoff = Backoff(multiplier = 2.0, delay = 1000, maxDelay = 64000)
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

    @Suppress("UNCHECKED_CAST")
    fun <T : Notification> getProcessor(notification: T): NotificationProcessor<T>? {
        val exactProcessor = processorMap[notification::class.java] as? NotificationProcessor<T>
        if (exactProcessor != null) return exactProcessor
        
        return processorMap.entries
            .firstOrNull { (clazz, _) -> clazz.isInstance(notification) }
            ?.value as? NotificationProcessor<T>
    }

    companion object {
        private val logger = KotlinLogging.logger {  }
    }
}