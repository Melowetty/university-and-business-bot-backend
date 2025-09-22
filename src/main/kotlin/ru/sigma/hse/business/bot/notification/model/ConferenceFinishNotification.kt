package ru.sigma.hse.business.bot.notification.model

import ru.sigma.hse.business.bot.notification.base.SimpleTextUrlButtonNotification

class ConferenceFinishNotification(
    private val surveyUrl: String
) : SimpleTextUrlButtonNotification {
    override fun getMessageText(): String {
        return "🎉 *Конференция окончена!*" +
                "\n\n" +
                "Огромное спасибо за участие! 🙏 Не забудь пройти опрос — это займёт всего пару минут, "+
                "а твои заработанные очки - 🔥 УДВОЯТСЯ ! 🔥" +
                "\n\n" +
                "Удачи и отличного дня! ✨"
    }
    override fun getButtonText(): String {
        return "Перейти к опросу 📝"
    }

    override fun getButtonUrl(): String {
        return surveyUrl
    }
}