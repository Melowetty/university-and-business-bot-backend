package ru.sigma.hse.business.bot.notification.processor

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.ParseMode
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow
import org.telegram.telegrambots.meta.generics.TelegramClient
import ru.sigma.hse.business.bot.notification.base.NotificationProcessor
import ru.sigma.hse.business.bot.notification.base.SimpleTextUrlButtonNotification

@Component
class SimpleTextUrlButtonProcessor : NotificationProcessor<SimpleTextUrlButtonNotification> {
    override fun process(
        bot: TelegramClient,
        telegramId: Long,
        notification: SimpleTextUrlButtonNotification
    ) {
        val message = notification.getMessageText()

        val keyboardBuilder = InlineKeyboardMarkup.builder()

        val row = run {
            val button = InlineKeyboardButton.builder()
                .text(notification.getButtonText())
                .url(notification.getButtonUrl())
                .build()

            InlineKeyboardRow(button)
        }

        keyboardBuilder.keyboardRow(row)

        val sendMessage = SendMessage.builder()
            .chatId(telegramId)
            .text(message)
            .parseMode(ParseMode.MARKDOWN)
            .replyMarkup(keyboardBuilder.build())
            .build()

        bot.execute(sendMessage)
    }

    override fun getNotificationType(): Class<SimpleTextUrlButtonNotification> {
        return SimpleTextUrlButtonNotification::class.java
    }
}