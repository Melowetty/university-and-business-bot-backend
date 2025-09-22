package ru.sigma.hse.business.bot.notification.model

import ru.sigma.hse.business.bot.notification.base.SimpleTextNotification

class ConferenceStartNotification : SimpleTextNotification {
    override fun getMessageText(): String {
        return "Доброе утро! ☀️" +
                "\n" +
                "До конференции остался один час, очень ждём тебя! Уже сейчас ты можешь изучить, что будет на конференции 😉"

    }
}