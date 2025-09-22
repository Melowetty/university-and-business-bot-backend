package ru.sigma.hse.business.bot.notification.model

import ru.sigma.hse.business.bot.notification.base.SimpleTextButtonNotification

class ConferenceFinishNotification : SimpleTextButtonNotification {
    override fun getMessageText(): String {
        return "🎉 Конференция окончена!" +
                "\n" +
                "Огромное спасибо за участие! 🙏 Не забудь пройти опрос — это займёт всего пару минут,"+
                "\n" +
                "а твои заработанные очки - 🔥 УДВОЯТСЯ ! 🔥" +
                "\n" +
                "Удачи и отличного дня! ✨"
    }
    override fun getButtonText(): String {
        return "Опрос"
    }

    override fun getButtonCallback(): String {
        return "IDK"
    }
}