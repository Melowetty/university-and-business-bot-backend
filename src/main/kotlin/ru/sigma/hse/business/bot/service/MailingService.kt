package ru.sigma.hse.business.bot.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import ru.sigma.hse.business.bot.notification.base.Notification
import ru.sigma.hse.business.bot.notification.impl.BatchNotificationService
import ru.sigma.hse.business.bot.persistence.UserStorage
import ru.sigma.hse.business.bot.utils.Paginator

@Service
class MailingService(
    private val batchNotificationService: BatchNotificationService,
    private val userStorage: UserStorage,
) {
    fun sendMailing(notification: Notification) {
        Paginator.fetchPageable(
            limit = 200,
            fetchFunction = { limit, token ->
                userStorage.getUsers(limit, token)
            }
        ) { users ->
            logger.info { "Fetch users for mailing ${notification.javaClass.simpleName}" }
            val telegramIds = userStorage.getTelegramIdsByIds(users.map { it.id })
            batchNotificationService.notify(telegramIds, notification)
        }
    }

    companion object {
        private val logger = KotlinLogging.logger {  }
    }
}