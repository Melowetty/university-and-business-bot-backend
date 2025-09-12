package ru.sigma.hse.business.bot.notification.model

import ru.sigma.hse.business.bot.notification.base.SimpleTextNotification

class ConferenceCompleteNotification : SimpleTextNotification {
    override fun getMessageText(): String {
        return "Ты успешно завершил конференцию! Поздравляем!!!"
    }
}