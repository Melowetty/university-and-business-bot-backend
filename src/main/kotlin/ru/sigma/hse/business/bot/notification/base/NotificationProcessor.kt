package ru.sigma.hse.business.bot.notification.base

import org.telegram.telegrambots.meta.generics.TelegramClient

interface NotificationProcessor<T : Notification> {
    fun process(bot: TelegramClient, telegramId: Long, notification: T)
    fun getNotificationType(): Class<T>
}