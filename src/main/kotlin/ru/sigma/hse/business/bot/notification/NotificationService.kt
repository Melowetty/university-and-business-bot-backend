package ru.sigma.hse.business.bot.notification

import ru.sigma.hse.business.bot.notification.base.Notification

interface NotificationService {
    fun notify(telegramId: Long, notification: Notification)
}