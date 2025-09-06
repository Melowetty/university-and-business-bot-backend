package ru.sigma.hse.business.bot.notification.processor

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.ParseMode
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.generics.TelegramClient
import ru.sigma.hse.business.bot.notification.base.NotificationProcessor
import ru.sigma.hse.business.bot.notification.base.SimpleTextNotification

@Component
class SimpleTextNotificationProcessor: NotificationProcessor<SimpleTextNotification> {
    override fun process(
        bot: TelegramClient,
        telegramId: Long,
        notification: SimpleTextNotification
    ) {
        val message = notification.getMessageText()

        val sendMessage = SendMessage.builder()
            .chatId(telegramId)
            .text(message)
            .parseMode(ParseMode.MARKDOWN)
            .build()

        bot.execute(sendMessage)
    }

    override fun getNotificationType(): Class<SimpleTextNotification> {
        return SimpleTextNotification::class.java
    }

}