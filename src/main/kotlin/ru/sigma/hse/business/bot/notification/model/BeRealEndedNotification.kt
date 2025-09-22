package ru.sigma.hse.business.bot.notification.model

import ru.sigma.hse.business.bot.notification.base.SimpleTextNotification

class BeRealEndedNotification : SimpleTextNotification {
    override fun getMessageText(): String {
        return "🔒 Время на отправку BeReal закончилось"
    }
}