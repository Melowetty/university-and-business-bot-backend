package ru.sigma.hse.business.bot.notification.processor

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.ParseMode
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow
import org.telegram.telegrambots.meta.generics.TelegramClient
import ru.sigma.hse.business.bot.notification.base.ManyButtonsTextMessage
import ru.sigma.hse.business.bot.notification.base.NotificationProcessor

@Component
class ButtonsTextMessageProcessor : NotificationProcessor<ManyButtonsTextMessage> {
    override fun process(
        bot: TelegramClient,
        telegramId: Long,
        notification: ManyButtonsTextMessage
    ) {
        val message = notification.getTextMessage()

        val keyboardBuilder = InlineKeyboardMarkup.builder()

        val rows = notification.getButtons().map {
            val button = InlineKeyboardButton.builder()
                .text(it.text)
                .callbackData(it.callback)
                .build()

            InlineKeyboardRow(button)
        }

        rows.forEach {
            keyboardBuilder.keyboardRow(it)
        }

        val sendMessage = SendMessage.builder()
            .chatId(telegramId)
            .text(message)
            .parseMode(ParseMode.MARKDOWN)
            .replyMarkup(keyboardBuilder.build())
            .build()

        bot.execute(sendMessage)
    }

    override fun getNotificationType(): Class<ManyButtonsTextMessage> {
        return ManyButtonsTextMessage::class.java
    }
}